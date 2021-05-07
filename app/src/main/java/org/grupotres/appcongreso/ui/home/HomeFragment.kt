package org.grupotres.appcongreso.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.databinding.FragmentHomeBinding
import org.grupotres.appcongreso.ui.login.HomeActivity

class HomeFragment : Fragment() {

	companion object {
		private const val RC_SIGN_IN = 120
	}

	private var binding: FragmentHomeBinding? = null
	private lateinit var googleSignInClient: GoogleSignInClient
	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var mAuth: FirebaseAuth

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
		mAuth = FirebaseAuth.getInstance()
	}


	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentHomeBinding.inflate(inflater, container, false)


		return binding?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		// TODO Add your menu entries here
		super.onCreateOptionsMenu(menu, inflater)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle item selection
		return when (item.itemId) {
			R.id.action_login -> {
				var modelDialog = AlertDialog.Builder(requireActivity())
				val dialogVista = layoutInflater.inflate(R.layout.fragment_user_login, null)
				val btnRedireccion = dialogVista.findViewById<Button>(R.id.acceder)
				modelDialog.setView(dialogVista)
				var dialogUserRedirect = modelDialog.create()
				dialogUserRedirect.show()

				mAuth = FirebaseAuth.getInstance()
				val user = mAuth.currentUser

				btnRedireccion.setOnClickListener() {
					if (user != null){
						val dashboardIntent = Intent(requireActivity(), HomeActivity::class.java)
						startActivity(dashboardIntent)
						requireActivity().finish()
					}else{
						dialogUserRedirect.dismiss()
						signIn()
					}
				}

				true
			}
			else -> super.onOptionsItemSelected(item)
		}
	}

	private fun signIn() {
		val signInIntent = googleSignInClient.signInIntent
		startActivityForResult(signInIntent, HomeFragment.RC_SIGN_IN)
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
		if (requestCode == HomeFragment.RC_SIGN_IN) {
			val task = GoogleSignIn.getSignedInAccountFromIntent(data)
			val exception = task.exception
			if (task.isSuccessful) {
				try {
					// Google Sign In was successful, authenticate with Firebase
					val account = task.getResult(ApiException::class.java)!!
					Log.d("MainActivity", "firebaseAuthWithGoogle:" + account.id)
					firebaseAuthWithGoogle(account.idToken!!)
				} catch (e: ApiException) {
					// Google Sign In failed, update UI appropriately
					Log.w("MainActivity", "Google sign in failed", e)
				}
			} else {
				Log.w("MainActivity", exception.toString())
			}
		}
	}

	private fun firebaseAuthWithGoogle(idToken: String) {
		val credential = GoogleAuthProvider.getCredential(idToken, null)
		mAuth.signInWithCredential(credential)
			.addOnCompleteListener(requireActivity()) { task ->
				if (task.isSuccessful) {
					// Sign in success, update UI with the signed-in user's information
					Log.d("MainActivity", "signInWithCredential:success")
					val intent = Intent(requireActivity(), HomeActivity::class.java)
					startActivity(intent)
					requireActivity().finish()
				} else {
					// If sign in fails, display a message to the user.
					Log.d("MainActivity", "signInWithCredential:failure")
				}
			}
	}
}