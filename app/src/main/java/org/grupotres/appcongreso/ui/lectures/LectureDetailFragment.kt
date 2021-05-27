package org.grupotres.appcongreso.ui.lectures

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.databinding.FragmentLectureDetailBinding
import org.grupotres.appcongreso.ui.feedback.FeedbackDialogFragment
import org.grupotres.appcongreso.util.JavaMailAPI
import org.grupotres.appcongreso.util.mainActivity
import org.grupotres.appcongreso.util.toEpoch
import java.lang.ref.WeakReference

class LectureDetailFragment : Fragment() {

	private var binding: FragmentLectureDetailBinding? = null
	private val args: LectureListFragmentArgs by navArgs()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.speaker_detail_enter)
//		postponeEnterTransition(500L, TimeUnit.MILLISECONDS)
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
			mainActivity.navigate(navDirections)
		}
		binding?.actionCalendar?.setOnClickListener { addToCalendar(args.lectureSpeaker.lecture) }
		binding?.actionMap?.setOnClickListener { findNavController().navigate(R.id.nav_map) }
		binding?.btnEnroll?.setOnClickListener { enrollToLecture() }
		binding?.actionFeedback?.setOnClickListener { showDialogFeedback() }
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

	@Suppress("DEPRECATION")
	private fun enrollToLecture() {
		val userEmail = mainActivity.auth.currentUser?.email
		val mail: String = userEmail.toString()
		val message = getString(R.string.email_message)
		val subject = "App Congreso 2021"

		//Send Mail
		val javaMailAPI = JavaMailAPI(WeakReference(requireContext()), mail, subject, message)
		javaMailAPI.execute()
	}

	private fun showDialogFeedback(){
		val dialog = FeedbackDialogFragment()
		dialog.show(parentFragmentManager, "FEEDBACK_DIALOG_FRAGMENT")
	}
}