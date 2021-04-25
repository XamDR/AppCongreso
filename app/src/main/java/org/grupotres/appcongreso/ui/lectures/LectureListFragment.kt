package org.grupotres.appcongreso.ui.lectures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.grupotres.appcongreso.databinding.FragmentLectureListBinding

class LectureListFragment : Fragment() {

	private var binding: FragmentLectureListBinding? = null
	private val lectureViewModel by viewModels<LectureViewModel>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentLectureListBinding.inflate(inflater, container, false)
		lectureViewModel.text.observe(viewLifecycleOwner, {
			binding?.textLectureList?.text = it
		})
		return binding?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}
}