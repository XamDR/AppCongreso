package org.grupotres.appcongreso.ui.resources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.grupotres.appcongreso.MainActivity
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.databinding.ItemResourceBinding

class ResourceAdapter(private val mainActivity: MainActivity, private val viewModel: ResourceViewModel) :
	ListAdapter<Lecture, ResourceAdapter.ResourceViewHolder>(ResourceCallback()) {

	class ResourceCallback : DiffUtil.ItemCallback<Lecture>() {

		override fun areItemsTheSame(oldResource: Lecture, newResource: Lecture)
				= oldResource.id == newResource.id

		override fun areContentsTheSame(oldResource: Lecture, newResource: Lecture)
				= oldResource == newResource
	}

	inner class ResourceViewHolder(private val binding: ItemResourceBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(lecture: Lecture) {
			binding.lectureTitle.text = lecture.title
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
		val binding = ItemResourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		val holder = ResourceViewHolder(binding)
		binding.download.setOnClickListener { downloadFiles(holder.adapterPosition) }
		return holder
	}

	override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
		val resource = viewModel.lectures.value?.get(position)
		resource?.let { holder.bind(resource) }
	}

	private fun downloadFiles(position: Int) {
		viewModel.downloadFiles(mainActivity, position, mainActivity.dbRef, mainActivity.storage)
	}
}
