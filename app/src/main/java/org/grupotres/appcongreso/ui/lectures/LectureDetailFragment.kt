package org.grupotres.appcongreso.ui.lectures

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.databinding.FragmentLectureDetailBinding
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class LectureDetailFragment : Fragment() {

	private var binding: FragmentLectureDetailBinding? = null
	private val args: LectureListFragmentArgs by navArgs()


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
		else -> super.onOptionsItemSelected(item)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initLectureDetails()
		binding?.btnMap?.setOnClickListener {
			findNavController().navigate(R.id.mapsFragment)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	private fun initLectureDetails() {
		binding?.lectureTitle?.text = args.lectureSpeaker.lecture.title
//		binding?.speakerName?.text = args.lectureSpeaker.speakers[0].toString()
		binding?.speakerPhoto?.load(args.lectureSpeaker.speakers[0].uriPhoto)
		binding?.lectureDetail?.text = args.lectureSpeaker.lecture.url
	}

	@SuppressLint("QueryPermissionsNeeded")
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
		val pattern = DateTimeFormatter.ofPattern("d/MM/yyyy K:mm a")
		val date = LocalDateTime.parse(datestring, pattern)
		val zone = ZoneId.of("America/Lima")
		return date.atZone(zone).toInstant().toEpochMilli()
	}
}





