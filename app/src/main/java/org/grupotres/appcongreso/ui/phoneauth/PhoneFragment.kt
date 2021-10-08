package org.grupotres.appcongreso.ui.phoneauth

import android.os.Bundle
import android.util.Log
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
import org.grupotres.appcongreso.databinding.FragmentPhoneBinding
import org.grupotres.appcongreso.util.mainActivity
import java.util.concurrent.TimeUnit

class PhoneFragment : BottomSheetDialogFragment() {

	private lateinit var auth: FirebaseAuth
	lateinit var storedVerificationId:String
	lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
	private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
	private var usuario: String? = null

	private var binding: FragmentPhoneBinding? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		usuario = arguments?.getString("user")
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentPhoneBinding.inflate(inflater, container, false)
		auth =FirebaseAuth.getInstance()

//        Reference
		val login = binding!!.loginBtn
		//val Login=findViewById<Button>(R.id.loginBtn)

//
//		var currentUser = auth.currentUser
//		if(currentUser != null) {
//			startActivity(Intent(activity, Home::class.java))
//			this.getActivity()?.finish()
//			//finish()
//		}

		login.setOnClickListener{
			login()
		}

		// Callback function for Phone Auth
		callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

			override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//				startActivity(Intent(context, Home::class.java))
//				activity?.finish()
			}

			override fun onVerificationFailed(e: FirebaseException) {
				Toast.makeText(context, "Falló", Toast.LENGTH_LONG).show()
			}

			override fun onCodeSent(
				verificationId: String,
				token: PhoneAuthProvider.ForceResendingToken
			) {

				Log.d("TAG","onCodeSent:$verificationId")
				storedVerificationId = verificationId
				resendToken = token

				dismiss()

				val dialog = usuario?.let { VerifyFragment.newInstance(storedVerificationId, it) }
				dialog?.show(parentFragmentManager, "VERIFY_DIALOG_FRAGMENT")
			}
		}
		return binding?.root
	}
	private fun login() {
		val mobileNumber= binding!!.phoneNumber
		//val mobileNumber=findViewById<EditText>(R.id.phoneNumber)
		var number=mobileNumber.text.toString().trim()

		if (number.isNotEmpty()) {
			number= "+51$number"
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
		fun newInstance(value: String): PhoneFragment {
			val fragment = PhoneFragment()
			val bundle = Bundle().apply {
				putString("user", value)
			}
			fragment.arguments = bundle
			return fragment
		}
	}
}