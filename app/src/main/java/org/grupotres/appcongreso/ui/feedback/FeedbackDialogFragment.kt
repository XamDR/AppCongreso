package org.grupotres.appcongreso.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.grupotres.appcongreso.databinding.FragmentFeedbackDialogBinding
import org.grupotres.appcongreso.ui.lectures.LectureListFragmentArgs

class FeedbackDialogFragment(idLecture: String) : BottomSheetDialogFragment() {

	private var binding: FragmentFeedbackDialogBinding? = null
	private var idU = idLecture
	private val db = FirebaseFirestore.getInstance()
	lateinit var auth: FirebaseAuth

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentFeedbackDialogBinding.inflate(inflater, container, false)
		binding?.btnSendMessage?.setOnClickListener { sendFeedback() }
		return binding?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	private fun sendFeedback(){
		auth = FirebaseAuth.getInstance()

		val userEmail = auth.currentUser?.email.toString()
		val name = auth.currentUser?.displayName
		val primaryKey = "$userEmail$idU"
		val rating = binding?.lectureRating?.rating


		db.collection("comentarios").document(primaryKey).set(
			hashMapOf(
				"primaryKey" to primaryKey,
				"idLecture" to idU,
				"nameUser" to name,
				"rating" to rating,
				"coment" to binding?.comment?.text.toString()
			)
		)
		onDestroyView()
	}
}