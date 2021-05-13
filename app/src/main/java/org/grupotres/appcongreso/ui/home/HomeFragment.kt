package org.grupotres.appcongreso.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import org.grupotres.appcongreso.MainActivity
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

	private var binding: FragmentHomeBinding? = null
	private lateinit var googleSignInClient: GoogleSignInClient
	private lateinit var auth: FirebaseAuth

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)

		// Configure Google Sign In
		val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestIdToken(getString(R.string.default_web_client_id))
			.requestEmail()
			.build()

		googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

		//Firebase Auth instance
		auth = FirebaseAuth.getInstance()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle item selection
		return when (item.itemId) {
			R.id.action_login -> {
				showAlertDialog()
				true
			}
			else -> super.onOptionsItemSelected(item)
		}
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

	private fun showAlertDialog() {
		MaterialAlertDialogBuilder(requireContext())
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

	private fun firebaseAuthWithGoogle(idToken: String) {
		val credential = GoogleAuthProvider.getCredential(idToken, null)
		auth.signInWithCredential(credential)
			.addOnCompleteListener(requireActivity()) { task ->
				if (task.isSuccessful) {
					// Sign in success, update UI with the signed-in user's information
					Log.d("MainActivity", "signInWithCredential:success")
					addItemsToNavigationDrawer()
					findNavController().navigate(R.id.action_home)
					val user = auth.currentUser
					if (user != null) {
						loadUserPhoto(user)
					}
				}
				else {
					// If sign in fails, display a message to the user.
					Log.d("MainActivity", "signInWithCredential:failure")
					Toast.makeText(requireContext(), "Error al autenticar.", Toast.LENGTH_SHORT).show()
				}
			}
	}

	private fun loadUserPhoto(user: FirebaseUser?) {
		val uri = user?.photoUrl

		if (uri != null) {
			val imageView = (requireActivity() as MainActivity).binding.navView.findViewById<ImageView>(R.id.imageView)
			imageView.load(uri)
		}
	}

	private fun addItemsToNavigationDrawer(){
		val navigationView = (requireActivity() as MainActivity).binding.navView
		val navMenu = navigationView.menu
		navMenu.findItem(R.id.nav_resources).isVisible = true
	}

	companion object {
		private const val RC_SIGN_IN = 9001
	}
}