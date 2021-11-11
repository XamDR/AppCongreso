package org.grupotres.appcongreso.ui.lectures

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.databinding.FragmentLectureDetailBinding
import org.grupotres.appcongreso.ui.feedback.FeedbackDialogFragment
import org.grupotres.appcongreso.ui.phoneauth.PhoneFragment
import org.grupotres.appcongreso.util.mainActivity
import org.grupotres.appcongreso.util.showSnackbar
import org.grupotres.appcongreso.util.toEpoch

class LectureDetailFragment : Fragment() {
	private val db = FirebaseFirestore.getInstance()
	private var binding: FragmentLectureDetailBinding? = null
	private val viewModel by viewModels<ResourceViewModel>()
	private val args: LectureListFragmentArgs by navArgs()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.speaker_detail_enter)
		binding = FragmentLectureDetailBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initLectureDetails()
		mainActivity.setOnLoginListener { setupBottomMenu(it) }
		setupBottomMenu(mainActivity.auth.currentUser != null)
		binding?.speakerPhoto?.setOnClickListener {
			val id = (it as ImageView).tag
			val speaker = args.lectureSpeaker.speakers.first { speaker -> speaker.id == id }
			val navDirections = LectureDetailFragmentDirections.actionLectureDetailFragmentToSpeakerDetailFragment(speaker)
			val extras = FragmentNavigatorExtras(binding?.speakerPhoto!! to "photo")
			mainActivity.navigate(navDirections, extras)
		}
		binding?.actionCalendar?.setOnClickListener { addToCalendar(args.lectureSpeaker.lecture) }
		binding?.actionMap?.setOnClickListener { findNavController().navigate(R.id.nav_map) }
		binding?.actionResources?.setOnClickListener { downloadResources() }
		binding?.btnEnroll?.setOnClickListener { enrollToLecture() }
		binding?.actionFeedback?.setOnClickListener { showDialogFeedback() }
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	private fun initLectureDetails() {
		val idS = args.lectureSpeaker.lecture.id
		val idLecture = "lecture" + idS;

		binding?.lectureTitle?.text = args.lectureSpeaker.lecture.title
		binding?.lectureDate?.text = getString(R.string.lecture_date_time, args.lectureSpeaker.lecture.getDate())

		db.collection("lectures").document(idLecture).get().addOnSuccessListener {
			var remCoupons = (it.get("capacity").toString() as String?)
			binding?.lectureContador?.text  = remCoupons
		}


		binding?.lectureDetail?.text  = args.lectureSpeaker.lecture.description
		binding?.speakerName?.text = args.lectureSpeaker.speakers[0].toString()
		binding?.speakerCompany?.text = args.lectureSpeaker.speakers[0].company
		binding?.speakerPhoto?.load(args.lectureSpeaker.speakers[0].uriPhoto)
		binding?.speakerPhoto?.tag = args.lectureSpeaker.speakers[0].id
	}

	private fun setupBottomMenu(result: Boolean) {
		binding?.actionResources?.visibility = if (result) View.VISIBLE else View.GONE
		binding?.empty?.visibility = if (result) View.VISIBLE else View.GONE
		binding?.bottomMenu?.fabAlignmentMode = if (result) BottomAppBar.FAB_ALIGNMENT_MODE_CENTER else BottomAppBar.FAB_ALIGNMENT_MODE_END
	}

	private fun addToCalendar(lecture: Lecture) {
		val intent = Intent(Intent.ACTION_INSERT).apply {
			data = CalendarContract.Events.CONTENT_URI
			putExtra(CalendarContract.Events.TITLE, lecture.title)
			putExtra(CalendarContract.Events.DESCRIPTION, lecture.url)
			putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, lecture.startTime.toEpoch())
			putExtra(CalendarContract.EXTRA_EVENT_END_TIME, lecture.endTime.toEpoch())
		}
		if (intent.resolveActivity(requireContext().packageManager) != null) {
			startActivity(intent)
		}
	}

	private fun downloadResources() {
		binding?.root?.showSnackbar(message = R.string.download_files_message)
		viewModel.downloadFiles(requireContext(), args.lectureSpeaker.lecture.id,
			mainActivity.dbRef, mainActivity.storage)
	}

	private fun enrollToLecture() {
		val idS = args.lectureSpeaker.lecture.id
		val idLecture = "lecture" + idS;
		val cantidad = binding?.lectureContador?.text.toString();
		val usuario = mainActivity.auth.currentUser?.email
		Log.i("USER", usuario.toString())
		val dialog = usuario?.let{ PhoneFragment.newInstance(usuario, cantidad, idLecture) }
		dialog?.show(parentFragmentManager, "FEEDBACK_DIALOG_FRAGMENT")
		if (cantidad.toInt() ==0){

			showAlertDialog()
		}
	}
	private fun showAlertDialog() {
		context?.let {
			MaterialAlertDialogBuilder(it)
				.setTitle(getString(R.string.capacidad_title))
				.setMessage(getString(R.string.capacidad_message))
				.setPositiveButton(R.string.aceptar_button) { dialog, _ ->
					dialog.dismiss()
				}.also { it.show() }
		}
	}


	private fun showDialogFeedback() {
		val id = args.lectureSpeaker.lecture.id
		val dialog = FeedbackDialogFragment.newInstance(id)
		dialog.show(parentFragmentManager, "FEEDBACK_DIALOG_FRAGMENT")
	}
}