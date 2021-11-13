package org.grupotres.appcongreso.ui.lectures

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.data.AppRepository
import org.grupotres.appcongreso.databinding.FragmentLectureDayBinding
import org.grupotres.appcongreso.ui.helpers.INavigator
import org.grupotres.appcongreso.util.copyTextToClipboard
import org.grupotres.appcongreso.util.mainActivity

class FragmentLectureSecondRoom : Fragment() {

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
		setClickHandlers()
	}

	private fun setClickHandlers() {
		binding?.linkRoom?.text = getString(R.string.link_second_room)
		binding?.keyRoom?.text = getString(R.string.key_second_room)
		binding?.linkRoom?.setOnClickListener {
			startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.link_second_room))))
		}
		binding?.keyRoom?.setOnClickListener {
			copyTextToClipboard(requireContext(), binding?.keyRoom?.text!!)
		}
	}

	private fun setupRecyclerView() {
		viewModel.fetchLecturesByRoom("Sala2", "Sala1|Sala2").observe(viewLifecycleOwner) {
			lectureAdapter.submitList(it)
		}
		binding?.rvLectures?.apply { adapter = this@FragmentLectureSecondRoom.lectureAdapter }
	}
}