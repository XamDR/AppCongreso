package org.grupotres.appcongreso.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.grupotres.appcongreso.databinding.FragmentFeedbackDialogBinding
import org.grupotres.appcongreso.util.Empty
import org.grupotres.appcongreso.util.mainActivity

class FeedbackDialogFragment : BottomSheetDialogFragment() {

	private var binding: FragmentFeedbackDialogBinding? = null

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentFeedbackDialogBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val idLecture = arguments?.getString("idLecture") ?: String.Empty
		binding?.btnSendMessage?.setOnClickListener { sendFeedback(idLecture) }
	}

	private fun sendFeedback(idLecture: String){
		val userEmail = mainActivity.auth.currentUser?.email
		val name = mainActivity.auth.currentUser?.displayName
		val primaryKey = "$userEmail-$idLecture"
		val rating = binding?.lectureRating?.rating
		val comment = binding?.comment?.text.toString()

		mainActivity.dbRef.collection("comments").document(primaryKey).set(
			hashMapOf(
				"id" to primaryKey,
				"idLecture" to idLecture,
				"userName" to name,
				"comment" to comment,
				"rating" to rating,
			)
		)
		dismiss()
		android.util.Log.d("COMMENT", "Comentario Enviado")
	}

	companion object {
		fun newInstance(idLecture: String) : FeedbackDialogFragment {
			return FeedbackDialogFragment().apply {
				arguments = Bundle().apply {
					putString("idLecture", idLecture)
				}
			}
		}
	}
}