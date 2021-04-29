package org.grupotres.appcongreso.ui.speakers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.grupotres.appcongreso.databinding.FragmentSpeakerListBinding

class SpeakerListFragment : Fragment() {

	private var binding: FragmentSpeakerListBinding? = null
	private val speakerViewModel by viewModels<SpeakerViewModel>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentSpeakerListBinding.inflate(inflater, container, false)
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
		val speakerAdapter = SpeakerAdapter(speakerViewModel)
		speakerAdapter.submitList(speakerViewModel.speakers)
		binding?.rvSpeakers?.apply {
			adapter = speakerAdapter
			addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
		}
	}
}