package org.grupotres.appcongreso.ui.lectures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import org.grupotres.appcongreso.data.AppRepository
import org.grupotres.appcongreso.databinding.FragmentLectureListBinding
import org.grupotres.appcongreso.ui.helpers.INavigator

class LectureListFragment : Fragment() {

	private var binding: FragmentLectureListBinding? = null
	private val viewModel by viewModels<LectureViewModel> { LectureViewModelFactory(AppRepository.Instance) }

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentLectureListBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initRecyclerView()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	private fun initRecyclerView() {
		val lectureAdapter = LectureAdapter(viewModel, requireActivity() as INavigator)
		viewModel.lectures.observe(viewLifecycleOwner, { lectureAdapter.submitList(it) })
		binding?.rvLectures?.apply {
			adapter = lectureAdapter
			addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
		}
	}
}