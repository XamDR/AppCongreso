package org.grupotres.appcongreso.ui.lectures

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import edu.icontinental.congresoi40.R
import edu.icontinental.congresoi40.databinding.FragmentLectureDetailBinding
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.ui.feedback.FeedbackDialogFragment
import org.grupotres.appcongreso.util.mainActivity

class LectureDetailFragment : Fragment() {

	private var binding: FragmentLectureDetailBinding? = null
	private val args by navArgs<LectureListFragmentArgs>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.speaker_detail_enter)
		binding = FragmentLectureDetailBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initLectureDetails()
		binding?.speakerPhoto?.setOnClickListener {
//			val id = (it as ImageView).tag
			if (args.lecture.speaker != null) {
				val speaker = args.lecture.speaker!!
				val navDirections = LectureDetailFragmentDirections.actionLectureDetailFragmentToSpeakerDetailFragment(speaker)
				val extras = FragmentNavigatorExtras(binding?.speakerPhoto!! to "photo")
				mainActivity.navigate(navDirections, extras)
			}
		}
		binding?.calendar?.setOnClickListener { addToCalendar(args.lecture) }
		binding?.resources?.setOnClickListener { viewResources() }
		binding?.feedback?.setOnClickListener { showDialogFeedback() }
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	private fun initLectureDetails() {
		binding?.lectureTitle?.text = args.lecture.title
		binding?.lectureDate?.text = args.lecture.getDate()
		binding?.speakerCompany?.text = args.lecture.speaker?.company
		binding?.speakerName?.text = getString(R.string.speaker_name,
			args.lecture.speaker.toString(),
			args.lecture.speaker?.country
		)
	}

	private fun viewResources() {
		findNavController().navigate(R.id.nav_pdf_viewer)
	}

	private fun showDialogFeedback() {
		val id = args.lecture.id
		val dialog = FeedbackDialogFragment.newInstance(id)
		dialog.show(parentFragmentManager, "FEEDBACK_DIALOG_FRAGMENT")
	}

	@SuppressLint("QueryPermissionsNeeded")
	private fun addToCalendar(lecture: Lecture) {
		val intent = Intent(Intent.ACTION_INSERT).apply {
			data = CalendarContract.Events.CONTENT_URI
			putExtra(CalendarContract.Events.TITLE, lecture.title)
			putExtra(CalendarContract.Events.DESCRIPTION, lecture.url)
			putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, lecture.startTime)
			putExtra(CalendarContract.EXTRA_EVENT_END_TIME, lecture.endTime)
		}
		if (intent.resolveActivity(requireContext().packageManager) != null) {
			startActivity(intent)
		}
	}
}