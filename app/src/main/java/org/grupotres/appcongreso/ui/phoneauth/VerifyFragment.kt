package org.grupotres.appcongreso.ui.phoneauth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.icontinental.congresoi40.R
import edu.icontinental.congresoi40.databinding.FragmentVerifyBinding
import org.grupotres.appcongreso.util.JavaMailAPI
import org.grupotres.appcongreso.util.debug
import java.lang.ref.WeakReference

class VerifyFragment : BottomSheetDialogFragment() {

	private var binding: FragmentVerifyBinding? = null
	private lateinit var auth: FirebaseAuth
	private var idVerification: String? = null
	private var userReal: String? = null
	private var idLecture: Long? = null
	private var capacity: Int? = null
	private var url: String? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		auth = FirebaseAuth.getInstance()
		idVerification = arguments?.getString("verification")
		userReal = arguments?.getString("user")
		idLecture = arguments?.getLong("idLecture")
		capacity = arguments?.getInt("capacity")
		url = arguments?.getString("url")
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
					val message = getString(R.string.email_message, url)
					val subject = getString(R.string.email_subject)
					Log.d("EMAIL", userReal + message + subject)

					//Send Mail
					val javaMailAPI = JavaMailAPI(WeakReference(context), mail, subject, message)
					javaMailAPI.execute()

					// Update lecture capacity
					updateLectureCapacity()
					dismiss()
				}
				else if (task.exception is FirebaseAuthInvalidCredentialsException) {
					Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show()
				}
			}
	}

	private fun updateLectureCapacity() {
		val db = Firebase.firestore
		val lectureRef = db.collection("lectures").document("lecture${idLecture}")
		val newCapacity = capacity?.minus(1)
		lectureRef.update("capacity", newCapacity).addOnSuccessListener {
			debug("FIRESTORE", "Cupos disponibles de la ponencia actualizado.")
		}
	}

	companion object {
		fun newInstance(value1: String, value2: String, idLecture: Long, capacity: Int, url: String): VerifyFragment {
			val fragment = VerifyFragment()
			val bundle = Bundle().apply {
				putString("verification", value1)
				putString("user", value2)
				putLong("idLecture", idLecture)
				putInt("capacity", capacity)
				putString("url", url)
			}
			fragment.arguments = bundle
			return fragment
		}
	}
}