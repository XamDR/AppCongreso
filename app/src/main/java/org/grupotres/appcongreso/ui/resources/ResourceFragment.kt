package org.grupotres.appcongreso.ui.resources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import org.grupotres.appcongreso.databinding.FragmentResourcesBinding
import org.grupotres.appcongreso.util.mainActivity

class ResourceFragment : Fragment() {

	private var binding: FragmentResourcesBinding? = null
	private val viewModel by viewModels<ResourceViewModel> {
		ResourceViewModelFactory(mainActivity.dbRef)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentResourcesBinding.inflate(inflater, container, false)

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
		val resourceAdapter = ResourceAdapter(mainActivity, viewModel)
		viewModel.lectures.observe(viewLifecycleOwner, { resourceAdapter.submitList(it) })
		binding?.rvResources?.apply {
			adapter = resourceAdapter
			addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
		}
	}

}
