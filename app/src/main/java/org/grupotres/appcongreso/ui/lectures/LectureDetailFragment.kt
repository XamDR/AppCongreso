package org.grupotres.appcongreso.ui.lectures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.grupotres.appcongreso.databinding.FragmentLectureDetailBinding

class LectureDetailFragment : Fragment() {

	private var binding: FragmentLectureDetailBinding? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentLectureDetailBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val title = arguments?.getString("title")

		if (title != null) {
			binding?.lectureTitle?.text = title
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}
}