package org.grupotres.appcongreso.ui.phoneauth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.databinding.FragmentVerifyBinding
import org.grupotres.appcongreso.util.JavaMailAPI
import java.lang.ref.WeakReference

class VerifyFragment : DialogFragment() {

	private var binding:FragmentVerifyBinding ? = null
	private lateinit var auth: FirebaseAuth
	private var idVerification: String? = null
	private var userReal: String? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		auth = FirebaseAuth.getInstance()
		idVerification = arguments?.getString("verification")
		userReal = arguments?.getString("user")
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentVerifyBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// Reference
		val otpGiven = binding?.idOtp

		binding?.verifyBtn?.setOnClickListener {
			val otp = otpGiven?.text.toString().trim()

			if (otp.isNotEmpty()) {
				val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
					idVerification!!,
					otp
				)
				signInWithPhoneAuthCredential(credential)
			}
			else {
				Toast.makeText(context, "Ingrese OTP", Toast.LENGTH_SHORT).show()
			}
		}
	}

	@Suppress("DEPRECATION")
	private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
		auth.signInWithCredential(credential)
			.addOnCompleteListener(requireActivity()) { task ->
				if (task.isSuccessful) {

					val userEmail = userReal
					val mail: String = userEmail.toString()
					val message = getString(R.string.email_message)
					val subject = "App Congreso 2021"
					Log.d("EMAIL", userReal + message + subject)

					//Send Mail
					val javaMailAPI = JavaMailAPI(WeakReference(context), mail, subject, message)
					javaMailAPI.execute()
					dismiss()
				}
				else {
// Sign in failed, display a message and update the UI
					if (task.exception is FirebaseAuthInvalidCredentialsException) {
// The verification code entered was invalid
						Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show()
					}
				}
			}
	}

	companion object {
		fun newInstance(value1: String, value2: String): VerifyFragment {
			val fragment = VerifyFragment()
			val bundle = Bundle().apply {
				putString("verification", value1)
				putString("user", value2)
			}
			fragment.arguments = bundle
			return fragment
		}
	}
}