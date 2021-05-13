package org.grupotres.appcongreso

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import org.grupotres.appcongreso.databinding.ActivityMainBinding
import org.grupotres.appcongreso.ui.helpers.INavigator

class MainActivity : AppCompatActivity(), INavigator {

	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var binding: ActivityMainBinding

	// Google SignIn
	private lateinit var googleSignInClient: GoogleSignInClient
	private lateinit var auth: FirebaseAuth

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		setSupportActionBar(binding.contentMain.toolbar)
		setupNavigation()
		initGoogleSignIn()
	}

	private fun initGoogleSignIn() {
		val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestIdToken(getString(R.string.default_web_client_id))
			.requestEmail()
			.build()

		googleSignInClient = GoogleSignIn.getClient(this, gso)

		//Firebase Auth instance
		auth = FirebaseAuth.getInstance()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.main, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		return when (item.itemId) {
			R.id.action_login -> {
				showAlertDialog()
				true
			}
			else -> super.onOptionsItemSelected(item)
		}
	}

	override fun onSupportNavigateUp(): Boolean {
		val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		val navController = navHostFragment.navController
		return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
		if (requestCode == RC_SIGN_IN) {
			val task = GoogleSignIn.getSignedInAccountFromIntent(data)
			try {
				// Google Sign In was successful, authenticate with Firebase
				val account = task.result

				if (account != null) {
					account.idToken?.let { firebaseAuthWithGoogle(it) }
					Log.d("MainActivity", "firebaseAuthWithGoogle:" + account.id)
				}
			}
			catch (e: ApiException) {
				// Google Sign In failed, update UI appropriately
				Log.d("MainActivity", "Google sign in failed $e")
			}
		}
	}

	private fun firebaseAuthWithGoogle(idToken: String) {
		val credential = GoogleAuthProvider.getCredential(idToken, null)
		auth.signInWithCredential(credential)
			.addOnCompleteListener(this) { task ->
				if (task.isSuccessful) {
					// Sign in success, update UI with the signed-in user's information
					Log.d("MainActivity", "signInWithCredential:success")
					addItemsToNavigationDrawer()
					navigateBackToHome()
					val user = auth.currentUser
					if (user != null) {
						loadUserData(user)
					}
				}
				else {
					// If sign in fails, display a message to the user.
					Log.d("MainActivity", "signInWithCredential:failure")
					Toast.makeText(this, "Error al autenticar.", Toast.LENGTH_SHORT).show()
				}
			}
	}

	private fun navigateBackToHome() {
		val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		val navController = navHostFragment.navController
		navController.navigate(R.id.action_home)
	}

	private fun loadUserData(user: FirebaseUser) {
		val imageView = binding.navView.findViewById<ImageView>(R.id.imageView)
		val nameUser = binding.navView.findViewById<TextView>(R.id.nameUser)
		val nameEmail = binding.navView.findViewById<TextView>(R.id.emailUser)
		imageView.load(user.photoUrl)
		nameUser.text = user.displayName
		nameEmail.text = user.email
	}

	private fun addItemsToNavigationDrawer(){
		val navMenu = binding.navView.menu
		navMenu.findItem(R.id.nav_resources).isVisible = true
	}

	private fun setupNavigation() {
		val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		val navController = navHostFragment.navController
		appBarConfiguration = AppBarConfiguration(
			setOf(R.id.nav_home, R.id.nav_lecture_list, R.id.nav_speaker_list, R.id.nav_resources, R.id.nav_calendar),
			binding.drawerLayout
		)
		setupActionBarWithNavController(navController, appBarConfiguration)
		binding.navView.setupWithNavController(navController)
	}

	override fun navigate(navDirections: NavDirections) {
		val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		val navController = navHostFragment.navController
		navController.navigate(navDirections)
	}

	private fun showAlertDialog() {
		MaterialAlertDialogBuilder(this)
			.setTitle(getString(R.string.login_title))
			.setMessage(getString(R.string.login_message))
			.setNegativeButton(R.string.cancel_button) { dialog, _ -> dialog.dismiss() }
			.setPositiveButton(R.string.redirect_button) { dialog, _ ->
				dialog.dismiss()
				signIn()
			}.also { it.show() }
	}

	private fun signIn() {
		val signInIntent = googleSignInClient.signInIntent
		startActivityForResult(signInIntent, RC_SIGN_IN)
	}

	companion object {
		private const val RC_SIGN_IN = 9001
	}
}