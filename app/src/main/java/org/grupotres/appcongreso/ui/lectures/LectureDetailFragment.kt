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
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.google.android.material.bottomappbar.BottomAppBar
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.databinding.FragmentLectureDetailBinding
import org.grupotres.appcongreso.ui.feedback.FeedbackDialogFragment
import org.grupotres.appcongreso.ui.phoneauth.PhoneFragment
import org.grupotres.appcongreso.ui.settings.SettingsManager
import org.grupotres.appcongreso.util.mainActivity
import org.grupotres.appcongreso.util.showSnackbar
import org.grupotres.appcongreso.util.toEpoch

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

	override fun onStart() {
		super.onStart()
		val manager = SettingsManager(requireContext())

		if (manager.isFirstRun) {
			manager.isFirstRun = false

			TapTargetSequence(mainActivity).targets(
				TapTarget.forView(binding?.actionCalendar!!, getString(R.string.title_tutorial_btn_calendar), getString(R.string.tutorial_btn_calendar))
					.cancelable(false).tintTarget(true),
				TapTarget.forView(binding?.actionResources!!, getString(R.string.title_tutorial_btn_resources), getString(R.string.tutorial_btn_resources))
					.cancelable(false).tintTarget(true),
				TapTarget.forView(binding?.btnEnroll!!, getString(R.string.title_tutorial_btn_enroll), getString(R.string.tutorial_btn_enroll))
					.cancelable(false).tintTarget(true),
			).start()
		}
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
		val usuario = mainActivity.auth.currentUser?.email
		Log.i("USER", usuario.toString())
		val dialog = usuario?.let { PhoneFragment.newInstance(it) }
		dialog?.show(parentFragmentManager, "FEEDBACK_DIALOG_FRAGMENT")
	}

	private fun showDialogFeedback() {
		val id = args.lectureSpeaker.lecture.id
		val dialog = FeedbackDialogFragment.newInstance(id)
		dialog.show(parentFragmentManager, "FEEDBACK_DIALOG_FRAGMENT")
	}
}