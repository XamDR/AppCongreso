package org.grupotres.appcongreso.ui.lectures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import edu.icontinental.congresoi40.R
import edu.icontinental.congresoi40.databinding.FragmentLectureDetailBinding
import org.grupotres.appcongreso.ui.feedback.FeedbackDialogFragment
import org.grupotres.appcongreso.util.mainActivity
import org.grupotres.appcongreso.util.showSnackbar
import org.grupotres.appcongreso.util.toBulletedList

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
		binding?.lectureDate?.text = args.lectureSpeaker.lecture.getDate()
		binding?.speakerCompany?.text = args.lectureSpeaker.speakers[0].company
		binding?.speakerName?.text = getString(R.string.speaker_name,
			args.lectureSpeaker.speakers[0].toString(),
			args.lectureSpeaker.speakers[0].country
		)
		if (args.lectureSpeaker.lecture.url?.contains('|') == true) {
			val names = args.lectureSpeaker.lecture.url?.split('|')
			binding?.moderadorName?.text = names?.toBulletedList()
		}
		else {
			binding?.moderadorName?.text = args.lectureSpeaker.lecture.url
		}
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