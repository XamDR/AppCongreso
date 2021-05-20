package org.grupotres.appcongreso.ui.resources

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.grupotres.appcongreso.core.LectureResources
import org.grupotres.appcongreso.databinding.ItemResourceBinding

class ResourcesAdapter(private val context: Context, private val viewModel: ResourceViewModel) :
	ListAdapter<LectureResources, ResourcesAdapter.ResourceViewHolder>(ResourceCallback()) {

	inner class ResourceViewHolder(private val binding: ItemResourceBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(resource: LectureResources) {
			binding.lectureTitle.text = resource.lecture.title
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
		val binding = ItemResourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		binding.download.setOnClickListener {
			Toast.makeText(context, "CLICK", Toast.LENGTH_SHORT).show()
		}
		return ResourceViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
		val resource = viewModel.resources.value?.get(position)
		resource?.let { holder.bind(resource) }
	}

	class ResourceCallback : DiffUtil.ItemCallback<LectureResources>() {

		override fun areItemsTheSame(oldResource: LectureResources, newResource: LectureResources)
			= oldResource.lecture.id == newResource.lecture.id

		override fun areContentsTheSame(oldResource: LectureResources, newResource: LectureResources)
			= oldResource == newResource
	}
}
