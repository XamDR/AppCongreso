package org.grupotres.appcongreso.ui.lectures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import edu.icontinental.congresoi40.databinding.FragmentLectureDayBinding
import org.grupotres.appcongreso.data.AppRepository
import org.grupotres.appcongreso.ui.helpers.INavigator
import org.grupotres.appcongreso.util.mainActivity

class FragmentLectureFirstRoom : Fragment() {

	private var binding: FragmentLectureDayBinding? = null
	private val viewModel by viewModels<LectureViewModel> { LectureViewModelFactory(AppRepository.Instance) }
	private lateinit var lectureAdapter: LectureAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		lectureAdapter = LectureAdapter(mainActivity as INavigator)
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
		viewModel.fetchLecturesByRoom("Sala1", "Sala1|Sala2").observe(viewLifecycleOwner) {
			lectureAdapter.submitList(it)
		}
		binding?.rvLectures?.apply { adapter = this@FragmentLectureFirstRoom.lectureAdapter }
	}
}