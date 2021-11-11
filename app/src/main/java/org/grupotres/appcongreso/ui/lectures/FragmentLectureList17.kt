package org.grupotres.appcongreso.ui.lectures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.data.AppRepository
import org.grupotres.appcongreso.databinding.FragmentLectureDayBinding
import org.grupotres.appcongreso.ui.helpers.INavigator

class FragmentLectureList17 : Fragment() {

	private var binding: FragmentLectureDayBinding? = null
	private val viewModel by viewModels<LectureViewModel> { LectureViewModelFactory(AppRepository.Instance) }
	private lateinit var adapter: LectureAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		adapter = LectureAdapter(viewModel, requireActivity() as INavigator)
	}

	override fun onCreateView(inflater: LayoutInflater,
	                          container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		binding = FragmentLectureDayBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupRecyclerView()
	}

	private fun setupRecyclerView() {
		viewModel.lectures.observe(viewLifecycleOwner, { adapter.submitList(it) })
		binding?.rvLectures?.apply {
			adapter = this@FragmentLectureList17.adapter
			addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
				ContextCompat.getDrawable(requireContext(), R.drawable.item_divider)?.let { setDrawable(it) }
			})
		}
	}
}