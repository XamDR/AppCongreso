package org.grupotres.appcongreso.ui.lectures

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import org.grupotres.appcongreso.databinding.FragmentLectureDetailBinding
import org.grupotres.appcongreso.ui.speakers.SpeakerListFragmentArgs

class LectureDetailFragment : Fragment() {

	private var binding: FragmentLectureDetailBinding? = null
	private val args: LectureListFragmentArgs by navArgs()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentLectureDetailBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initLectureDetails()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	@SuppressLint("SetTextI18n")
	private fun initLectureDetails() {
		binding?.lectureTitle?.text = args.lectureSpeaker.lecture.title
		binding?.speakerName?.text = args.lectureSpeaker.speakers[0].name+' '+args.lectureSpeaker.speakers[0].surname+' '+args.lectureSpeaker.speakers[0].maternalSurname
		binding?.speakerPhoto?.load(args.lectureSpeaker.speakers[0].uriPhoto)
	}
}