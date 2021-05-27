package org.grupotres.appcongreso

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import coil.load
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.grupotres.appcongreso.databinding.ActivityMainBinding
import org.grupotres.appcongreso.ui.helpers.INavigator
import org.grupotres.appcongreso.util.setNightMode
import org.grupotres.appcongreso.util.showSnackbar

class MainActivity : AppCompatActivity(), INavigator {

	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var binding: ActivityMainBinding

	// Google SignIn
	private lateinit var googleSignInClient: GoogleSignInClient
	val auth: FirebaseAuth = FirebaseAuth.getInstance()

	// Firestore reference
	val dbRef = Firebase.firestore

	// Storage reference
	val storage = Firebase.storage

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		setSupportActionBar(binding.contentMain.toolbar)
		setNightMode(PreferenceManager.getDefaultSharedPreferences(this))
		setupNavigation()

		// We attach a listener to the drawer sliding event.
		// If slideOffset is greater than 0 it means that the drawer is opening.
		binding.drawerLayout.addDrawerListener(object: DrawerLayout.SimpleDrawerListener() {
			override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
				if (slideOffset > 0 && auth.currentUser != null) {
					loadUserData(auth.currentUser!!)
				}
			}
		})
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.main, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		return when (item.itemId) {
			R.id.action_login -> {
				if (auth.currentUser == null) {
					initGoogleSignIn()
					showAlertDialog()
				}
				else {
					auth.signOut()
					loadUserData(null)
					binding.root.showSnackbar(message = R.string.logout_message)
				}
				callback?.invoke(auth.currentUser != null)
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

	@Suppress("DEPRECATION")
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
				Toast.makeText(this, getString(R.string.error_login_message), Toast.LENGTH_SHORT).show()
			}
		}
	}

	private fun initGoogleSignIn() {
		val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestIdToken(getString(R.string.default_web_client_id))
			.requestEmail()
			.build()

		googleSignInClient = GoogleSignIn.getClient(this, gso)
	}

	private fun firebaseAuthWithGoogle(idToken: String) {
		lifecycleScope.launch {
			val credential = GoogleAuthProvider.getCredential(idToken, null)
			val result = auth.signInWithCredential(credential).await()

			if (result.user != null) {
				Log.d("MainActivity", "signInWithCredential:success")
				loadUserData(result.user!!)
			}
			else {
				Log.d("MainActivity", "signInWithCredential:failure")
				Toast.makeText(this@MainActivity, getString(R.string.error_auth), Toast.LENGTH_SHORT).show()
			}
			callback?.invoke(result.user != null)
		}
	}

	private fun loadUserData(user: FirebaseUser?) {
		binding.navView.findViewById<ImageView>(R.id.user_avatar).apply {
			load(user?.photoUrl)
			visibility = if (user != null) View.VISIBLE else View.GONE
		}
		binding.navView.findViewById<TextView>(R.id.user_name).apply {
			text = user?.displayName
			visibility = if (user != null) View.VISIBLE else View.GONE
		}
		binding.navView.findViewById<TextView>(R.id.user_email).apply {
			text = user?.email
			visibility = if (user != null) View.VISIBLE else View.GONE
		}
	}

	private fun setupNavigation() {
		val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		val navController = navHostFragment.navController
		appBarConfiguration = AppBarConfiguration(
			setOf(R.id.nav_home, R.id.nav_lecture_list, R.id.nav_info, R.id.nav_settings),
			binding.drawerLayout
		)
		setupActionBarWithNavController(navController, appBarConfiguration)
		binding.navView.setupWithNavController(navController)
	}

	override fun navigate(navDirections: NavDirections, extras: FragmentNavigator.Extras?) {
		val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		val navController = navHostFragment.navController
		if (extras != null) {
			navController.navigate(navDirections, extras)
		}
		else {
			navController.navigate(navDirections)
		}
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

	@Suppress("DEPRECATION")
	private fun signIn() {
		val signInIntent = googleSignInClient.signInIntent
		startActivityForResult(signInIntent, RC_SIGN_IN)
	}

	fun setOnLoginListener(callback: (Boolean) -> Unit) {
		this.callback = callback
	}

	private var callback: ((Boolean) -> Unit)? = null

	companion object {
		private const val RC_SIGN_IN = 9001
	}
}