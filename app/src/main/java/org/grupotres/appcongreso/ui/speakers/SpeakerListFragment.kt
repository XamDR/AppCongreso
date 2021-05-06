package org.grupotres.appcongreso.ui.speakers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import org.grupotres.appcongreso.data.AppRepository
import org.grupotres.appcongreso.databinding.FragmentSpeakerListBinding
import org.grupotres.appcongreso.ui.helpers.INavigator

class SpeakerListFragment : Fragment() {

	private var binding: FragmentSpeakerListBinding? = null
	private val viewModel by viewModels<SpeakerViewModel> { SpeakerViewModelFactory(AppRepository.Instance) }

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
		val speakerAdapter = SpeakerAdapter(viewModel, requireActivity() as INavigator)
		viewModel.speakers.observe(viewLifecycleOwner, { speakerAdapter.submitList(it) })
		binding?.rvSpeakers?.apply {
			adapter = speakerAdapter
			addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
		}
	}
}