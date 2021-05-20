package org.grupotres.appcongreso.ui.resources

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.grupotres.appcongreso.core.LectureSpeakers
import org.grupotres.appcongreso.core.Resource
import org.grupotres.appcongreso.databinding.FragmentResourcesDetailBinding
import org.grupotres.appcongreso.ui.helpers.INavigator

class ResourcesAdapter() :
	ListAdapter<Resource, ResourcesAdapter.ResourceViewHolder>(ResourceCallback()) {

	inner class ResourceViewHolder(private val binding: FragmentResourcesDetailBinding) :
		RecyclerView.ViewHolder(binding.root) {

		@SuppressLint("SetTextI18n")
		fun bind(resource: Resource) {
			binding.lectureTitle.text = resource.name
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
		val binding = FragmentResourcesDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		val holder = ResourceViewHolder(binding)
		return holder
	}

	override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
		//val lecture = viewModel.lectures.value?.get(position)
		//lecture?.let { holder.bind(it) }
	}

	class ResourceCallback : DiffUtil.ItemCallback<Resource>() {

		override fun areItemsTheSame(oldResource: Resource, newResource: Resource)
				= oldResource.id == newResource.id

		override fun areContentsTheSame(oldResource: Resource, newResource: Resource)
				= oldResource == newResource
	}
}
