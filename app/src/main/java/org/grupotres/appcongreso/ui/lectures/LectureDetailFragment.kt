package org.grupotres.appcongreso.ui.lectures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
//import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.load
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.databinding.FragmentLectureDetailBinding
import org.grupotres.appcongreso.ui.feedback.FeedbackDialogFragment
import org.grupotres.appcongreso.util.mainActivity
import org.grupotres.appcongreso.util.showSnackbar

class LectureDetailFragment : Fragment() {

	private var binding: FragmentLectureDetailBinding? = null
	private val viewModel by viewModels<ResourceViewModel>()
	private val args by navArgs<LectureListFragmentArgs>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.speaker_detail_enter)
		binding = FragmentLectureDetailBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initLectureDetails()
//		binding?.speakerPhoto?.setOnClickListener {
//			val id = (it as ImageView).tag
//			val speaker = args.lectureSpeaker.speakers.first { speaker -> speaker.id == id }
//			val navDirections = LectureDetailFragmentDirections.actionLectureDetailFragmentToSpeakerDetailFragment(speaker)
//			val extras = FragmentNavigatorExtras(binding?.speakerPhoto!! to "photo")
//			mainActivity.navigate(navDirections, extras)
//		}
		binding?.resources?.setOnClickListener { downloadResources() }
		binding?.feedback?.setOnClickListener { showDialogFeedback() }
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	private fun initLectureDetails() {
		binding?.lectureTitle?.text = args.lectureSpeaker.lecture.title
		binding?.lectureDate?.text = getString(R.string.lecture_date_time, args.lectureSpeaker.lecture.getDate())
		binding?.lectureDetail?.text = args.lectureSpeaker.lecture.description
		binding?.speakerName?.text = args.lectureSpeaker.speakers[0].toString()
		binding?.speakerCompany?.text = args.lectureSpeaker.speakers[0].company
		binding?.speakerPhoto?.load(args.lectureSpeaker.speakers[0].uriPhoto)
		binding?.speakerPhoto?.tag = args.lectureSpeaker.speakers[0].id
	}

	private fun downloadResources() {
		binding?.root?.showSnackbar(message = R.string.download_files_message)
		viewModel.downloadFiles(requireContext(), args.lectureSpeaker.lecture.id,
			mainActivity.dbRef, mainActivity.storage)
	}

	private fun showDialogFeedback() {
		val id = args.lectureSpeaker.lecture.id
		val dialog = FeedbackDialogFragment.newInstance(id)
		dialog.show(parentFragmentManager, "FEEDBACK_DIALOG_FRAGMENT")
	}
}