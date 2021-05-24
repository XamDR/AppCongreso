package org.grupotres.appcongreso.ui.lectures

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.load
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.databinding.FragmentLectureDetailBinding
import org.grupotres.appcongreso.util.mainActivity
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class LectureDetailFragment : Fragment() {

	private var binding: FragmentLectureDetailBinding? = null
	private val args: LectureListFragmentArgs by navArgs()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.speaker_detail_enter)
//		postponeEnterTransition(500L, TimeUnit.MILLISECONDS)
		binding = FragmentLectureDetailBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.menu_fragment_lecture_detail, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
		R.id.action_calendar -> {
			addToCalendar(args.lectureSpeaker.lecture)
			true
		}
		R.id.action_map -> {
			findNavController().navigate(R.id.mapsFragment)
			true
		}
		else -> super.onOptionsItemSelected(item)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initLectureDetails()
		binding?.speakerPhoto?.setOnClickListener {
			val id = (it as ImageView).tag
			val speaker = args.lectureSpeaker.speakers.first { speaker -> speaker.id == id }
			val navDirections = LectureDetailFragmentDirections.actionLectureDetailFragmentToSpeakerDetailFragment(speaker)
			mainActivity.navigate(navDirections)
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
			putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, toEpoch(lecture.startTime))
			putExtra(CalendarContract.EXTRA_EVENT_END_TIME, toEpoch(lecture.endTime))
		}
		if (intent.resolveActivity(requireContext().packageManager) != null) {
			startActivity(intent)
		}

	}

	private fun toEpoch(datestring: String): Long {
		val pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy K:mm a")
		val date = LocalDateTime.parse(datestring, pattern)
		val zone = ZoneId.of("America/Lima")
		return date.atZone(zone).toInstant().toEpochMilli()
	}
}





