package org.grupotres.appcongreso.ui.info

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import org.grupotres.appcongreso.databinding.InfoFragmentBinding
import java.util.concurrent.TimeUnit

class InfoFragment : Fragment() {

	private var binding: InfoFragmentBinding? = null
	lateinit var auth: FirebaseAuth
	lateinit var storedVerificationId:String
	lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
	private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = InfoFragmentBinding.inflate(inflater, container, false)


		return binding?.root
	}


	private fun sendVerificationcode(number: String) {
		val options = PhoneAuthOptions.newBuilder(auth)
			.setPhoneNumber(number) // Phone number to verify
			.setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
			.setActivity(activity) // Activity (for callback binding)
			.setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
			.build()
		PhoneAuthProvider.verifyPhoneNumber(options)
	}
}