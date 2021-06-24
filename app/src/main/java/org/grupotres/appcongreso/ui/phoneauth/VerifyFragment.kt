package org.grupotres.appcongreso.ui.phoneauth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.*
import org.grupotres.appcongreso.databinding.FragmentVerifyBinding
import org.grupotres.appcongreso.ui.lectures.JavaMailAPI
import org.grupotres.appcongreso.util.mainActivity
import java.lang.ref.WeakReference


class VerifyFragment(storedVerification: String) : DialogFragment(){

	private var binding:FragmentVerifyBinding ? = null
	lateinit var auth: FirebaseAuth
	private var idVerification = storedVerification

	var task = FirebaseAuth.getInstance().signInAnonymously()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentVerifyBinding.inflate(inflater, container, false)

		auth= FirebaseAuth.getInstance()

		val storedVerificationId= idVerification;

//        Reference

		val otpGiven=binding?.idOtp

		binding?.verifyBtn?.setOnClickListener {
			var otp=otpGiven?.text.toString().trim()
			if(!otp.isEmpty()){
				val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
					storedVerificationId, otp
				)
				signInWithPhoneAuthCredential(credential)
			}else{
				Toast.makeText(context, "Ingrese OTP", Toast.LENGTH_SHORT).show()
			}

		}
		return binding?.root
	}

	private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
		auth.signInWithCredential(credential)
			.addOnCompleteListener(requireActivity()) { task ->
				if (task.isSuccessful) {

					val userEmail = mainActivity.auth.currentUser?.email
					val mail: String = userEmail.toString()
					val message = "mensaje"
					val subject = "App Congreso"

					//Send Mail
					val javaMailAPI = JavaMailAPI(WeakReference(requireContext()), mail, subject, message)
					javaMailAPI.execute()
					dismiss()

				} else {
// Sign in failed, display a message and update the UI
					if (task.exception is FirebaseAuthInvalidCredentialsException) {
// The verification code entered was invalid
						Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show()
					}
				}
			}
	}
}