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
import coil.load
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import edu.icontinental.congresoi40.R
import edu.icontinental.congresoi40.databinding.FragmentLectureDetailBinding
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.core.Speaker
import org.grupotres.appcongreso.ui.feedback.FeedbackDialogFragment
import org.grupotres.appcongreso.ui.phoneauth.PhoneFragment
import org.grupotres.appcongreso.ui.settings.SettingsManager
import org.grupotres.appcongreso.util.debug
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
			if (!args.lecture.isHeader) {
				val lecture = args.lecture

				val speaker = Speaker(
					surname = lecture.surnameSpeaker,
					maternalSurname = lecture.maternalSurnameSpeaker,
					name = lecture.nameSpeaker,
					country = lecture.countrySpeaker,
					company = lecture.companySpeaker,
					academicInfo = lecture.academicInfoSpeaker,
					uriPhoto = lecture.uriPhotoSpeaker
				)
				val navDirections = LectureDetailFragmentDirections.actionLectureDetailFragmentToSpeakerDetailFragment(speaker)
				val extras = FragmentNavigatorExtras(binding?.speakerPhoto!! to "photo")
				mainActivity.navigate(navDirections, extras)
			}
		}
		binding?.calendar?.setOnClickListener { addToCalendar(args.lecture) }
		binding?.resources?.setOnClickListener { viewResources() }
		binding?.feedback?.setOnClickListener { showDialogFeedback() }
		binding?.enroll?.setOnClickListener { enrollToLecture() }
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	override fun onStart() {
		super.onStart()
		val manager = SettingsManager(requireContext())

		if (manager.isFirstRun) {
			manager.isFirstRun = false

			TapTargetSequence(mainActivity).targets(
				TapTarget.forView(binding?.speakerPhoto!!, getString(R.string.title_tutorial_speaker), getString(R.string.tutorial_speaker_photo))
					.cancelable(false).tintTarget(true),
				TapTarget.forView(binding?.calendar!!, getString(R.string.title_tutorial_btn_calendar), getString(R.string.tutorial_btn_calendar))
					.cancelable(false).tintTarget(true),
				TapTarget.forView(binding?.feedback!!, getString(R.string.title_tutorial_btn_feedback), getString(R.string.tutorial_btn_feedback))
					.cancelable(false).tintTarget(true),
				TapTarget.forView(binding?.resources!!, getString(R.string.title_tutorial_btn_resources), getString(R.string.tutorial_btn_resources))
					.cancelable(false).tintTarget(true),
				TapTarget.forView(binding?.enroll!!, getString(R.string.title_tutorial_btn_enroll), getString(R.string.tutorial_btn_enroll))
					.cancelable(false).tintTarget(true),
			).start()
		}
	}

	private fun initLectureDetails() {
		binding?.lectureTitle?.text = args.lecture.title
		binding?.lectureDate?.text = args.lecture.getDate()
		binding?.lectureDescription?.text = args.lecture.description
		binding?.speakerCompany?.text = args.lecture.companySpeaker
		binding?.speakerName?.text = getString(R.string.speaker_name,
			args.lecture.nameSpeaker,
			args.lecture.surnameSpeaker,
			args.lecture.maternalSurnameSpeaker
		)
		binding?.speakerPhoto?.load(args.lecture.uriPhotoSpeaker)
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
			putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, lecture.startTime.time)
			putExtra(CalendarContract.EXTRA_EVENT_END_TIME, lecture.endTime.time)
		}
		if (intent.resolveActivity(requireContext().packageManager) != null) {
			startActivity(intent)
		}
	}

	private fun enrollToLecture() {
		val usuario = mainActivity.auth.currentUser?.email
		debug("USER", usuario)
		val dialog = usuario?.let { PhoneFragment.newInstance(it) }
		dialog?.show(parentFragmentManager, "FEEDBACK_DIALOG_FRAGMENT")
	}
}