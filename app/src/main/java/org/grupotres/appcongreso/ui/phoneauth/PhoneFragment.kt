package org.grupotres.appcongreso.ui.phoneauth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import edu.icontinental.congresoi40.databinding.FragmentPhoneBinding
import org.grupotres.appcongreso.util.debug
import org.grupotres.appcongreso.util.mainActivity
import java.util.concurrent.TimeUnit

class PhoneFragment : BottomSheetDialogFragment() {

	private lateinit var auth: FirebaseAuth
	lateinit var storedVerificationId:String
	lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
	private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
	private var user: String? = null
	private var idLecture: Long? = null
	private var capacity: Int? = null
	private var url: String? = null
	private var binding: FragmentPhoneBinding? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		user = arguments?.getString("user")
		idLecture = arguments?.getLong("idLecture")
		capacity = arguments?.getInt("capacity")
		url = arguments?.getString("url")
		auth = FirebaseAuth.getInstance()
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentPhoneBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding!!.loginBtn.setOnClickListener { login() }

		// Callback function for Phone Auth
		callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

			override fun onVerificationCompleted(credential: PhoneAuthCredential) { }

			override fun onVerificationFailed(e: FirebaseException) {
				Toast.makeText(context, "Falló", Toast.LENGTH_LONG).show()
			}
			override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
				debug("TAG","onCodeSent:$verificationId")
				storedVerificationId = verificationId
				resendToken = token
				dismiss()
				val dialog = user?.let {
					VerifyFragment.newInstance(storedVerificationId, it, idLecture!!, capacity!!, url!!)
				}
				dialog?.show(parentFragmentManager, "VERIFY_DIALOG_FRAGMENT")
			}
		}
	}

	private fun login() {
		var number = binding?.phoneNumber?.text.toString().trim()

		if (number.isNotEmpty()) {
			number = "+51$number"
			sendVerificationcode(number)
		}
		else {
			Toast.makeText(context,"Ingrese su número telefónico",Toast.LENGTH_SHORT).show()
		}
	}

	private fun sendVerificationcode(number: String) {
		val options = PhoneAuthOptions.newBuilder(auth)
			.setPhoneNumber(number) // Phone number to verify
			.setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
			.setActivity(mainActivity) // Activity (for callback binding)
			.setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
			.build()
		PhoneAuthProvider.verifyPhoneNumber(options)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	companion object {
		fun newInstance(value: String, idLecture: Long, capacity: Int, url: String): PhoneFragment {
			val fragment = PhoneFragment()
			val bundle = Bundle().apply {
				putString("user", value)
				putLong("idLecture", idLecture)
				putInt("capacity", capacity)
				putString("url", url)
			}
			fragment.arguments = bundle
			return fragment
		}
	}
}