package org.grupotres.appcongreso.ui.lectures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import org.grupotres.appcongreso.databinding.FragmentLectureDetailBinding
import org.grupotres.appcongreso.util.mainActivity

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

	private fun initLectureDetails() {
		binding?.lectureTitle?.text = args.lectureSpeaker.lecture.title
		binding?.speakerName?.text = args.lectureSpeaker.speakers[0].name
		binding?.speakerPhoto?.load(args.lectureSpeaker.speakers[0].uriPhoto)
		binding?.btnEnroll?.setOnClickListener { enrollToLecture() }
	}

	private fun enrollToLecture() {
		val userEmail = mainActivity.auth.currentUser?.email

		if (userEmail != null) {
			Toast.makeText(requireContext(), userEmail, Toast.LENGTH_SHORT).show()
		}
	}
}