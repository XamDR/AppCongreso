package org.grupotres.appcongreso.ui.lectures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.grupotres.appcongreso.databinding.LectureListHeaderBinding

class HeaderLectureAdapter(private val title: String) : RecyclerView.Adapter<HeaderLectureAdapter.HeaderViewHolder>() {

	class HeaderViewHolder(private val binding: LectureListHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

		fun bind(title: String) {
			binding.headerTitle.text = title
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
		val binding = LectureListHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return HeaderViewHolder(binding)
	}

	override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
		holder.bind(title)
	}

	override fun getItemCount() = 1
}