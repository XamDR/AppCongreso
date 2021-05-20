package org.grupotres.appcongreso.ui.resources

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration

import org.grupotres.appcongreso.databinding.FragmentResourcesBinding


class ResourceFragment : Fragment() {

	private var binding: FragmentResourcesBinding? = null
	private val viewModel by viewModels<ResourceViewModel>()

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
		val resourcesAdapter = ResourcesAdapter(requireContext(), viewModel)
		viewModel.resources.observe(viewLifecycleOwner, { resourcesAdapter.submitList(it) })
		binding?.rvResources?.apply {
			adapter = resourcesAdapter
			addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
		}
	}

}
