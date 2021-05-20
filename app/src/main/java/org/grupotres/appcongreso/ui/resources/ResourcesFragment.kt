package org.grupotres.appcongreso.ui.resources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import org.grupotres.appcongreso.data.AppRepository
import org.grupotres.appcongreso.databinding.FragmentLectureListBinding
import org.grupotres.appcongreso.databinding.FragmentResourcesBinding
import org.grupotres.appcongreso.ui.helpers.INavigator


class ResourcesFragment : Fragment() {

	private var binding: FragmentResourcesBinding? = null

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
		//val resourcesAdapter = ResourcesAdapter(viewModel, requireActivity() as INavigator)
		//viewModel.lectures.observe(viewLifecycleOwner, { resourcesAdapter.submitList(it) })
		//binding?.rvLectures?.apply {
		//	adapter = resourcesAdapter
		//		addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
		//}
	}
}
