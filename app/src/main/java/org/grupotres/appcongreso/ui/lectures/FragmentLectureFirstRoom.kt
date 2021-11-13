package org.grupotres.appcongreso.ui.lectures

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.data.AppRepository
import org.grupotres.appcongreso.databinding.FragmentLectureDayBinding
import org.grupotres.appcongreso.ui.helpers.INavigator
import org.grupotres.appcongreso.util.copyTextToClipboard
import org.grupotres.appcongreso.util.mainActivity

class FragmentLectureFirstRoom : Fragment() {

	private var binding: FragmentLectureDayBinding? = null
	private val viewModel by viewModels<LectureViewModel> { LectureViewModelFactory(AppRepository.Instance) }
	private lateinit var firstDayAdapter: LectureAdapter
	private lateinit var secondDayAdapter: LectureAdapter
	private lateinit var thirdDayAdapter: LectureAdapter
	private lateinit var firstDayHeaderAdapter: HeaderLectureAdapter
	private lateinit var secondDayHeaderAdapter: HeaderLectureAdapter
	private lateinit var thirdDayHeaderAdapter: HeaderLectureAdapter
	private lateinit var concatAdapter: ConcatAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		firstDayAdapter = LectureAdapter(mainActivity as INavigator)
		secondDayAdapter = LectureAdapter(mainActivity as INavigator)
		thirdDayAdapter = LectureAdapter(mainActivity as INavigator)
		firstDayHeaderAdapter = HeaderLectureAdapter("Miércoles 17")
		secondDayHeaderAdapter = HeaderLectureAdapter("Jueves 18")
		thirdDayHeaderAdapter = HeaderLectureAdapter("Viernes 19")
		concatAdapter = ConcatAdapter(
			firstDayHeaderAdapter,
			firstDayAdapter,
			secondDayHeaderAdapter,
			secondDayAdapter,
			thirdDayHeaderAdapter,
			thirdDayAdapter
		)
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
		binding?.linkRoom?.text = getString(R.string.link_first_room)
		binding?.keyRoom?.text = getString(R.string.key_first_room)
		binding?.linkRoom?.setOnClickListener {
			startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.link_first_room))))
		}
		binding?.keyRoom?.setOnClickListener {
			copyTextToClipboard(requireContext(), binding?.keyRoom?.text!!)
		}
	}

	private fun setupRecyclerView() {
		viewModel.fetchLecturesByRoom("Sala1").observe(viewLifecycleOwner) {
			firstDayAdapter.submitList(it.filter { l -> l.lecture.day == "Miercoles" })
			secondDayAdapter.submitList(it.filter { l -> l.lecture.day == "Jueves" })
			thirdDayAdapter.submitList(it.filter { l -> l.lecture.day == "Viernes" })
		}
		binding?.rvLectures?.apply {
			adapter = this@FragmentLectureFirstRoom.concatAdapter
		}
	}
}