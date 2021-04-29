package org.grupotres.appcongreso.ui.lectures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.databinding.ItemLectureDetailBinding

class LectureAdapter(private val viewModel: LectureViewModel) :
	ListAdapter<Lecture, LectureAdapter.LectureViewHolder>(LectureCallback()) {

	inner class LectureViewHolder(private val binding: ItemLectureDetailBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(lecture: Lecture) {
			binding.lectureTitle.text = lecture.title
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureViewHolder {
		val binding = ItemLectureDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return LectureViewHolder(binding)
	}

	override fun onBindViewHolder(holder: LectureViewHolder, position: Int) {
		val lecture = viewModel.lectures[position]
		holder.bind(lecture)
	}

	class LectureCallback : DiffUtil.ItemCallback<Lecture>() {

		override fun areItemsTheSame(oldLecture: Lecture, newLecture: Lecture) = oldLecture.id == newLecture.id

		override fun areContentsTheSame(oldLecture: Lecture, newLecture: Lecture) = oldLecture == newLecture
	}
}