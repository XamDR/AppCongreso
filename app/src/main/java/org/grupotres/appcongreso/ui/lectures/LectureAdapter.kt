package org.grupotres.appcongreso.ui.lectures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.databinding.ItemLectureDetailBinding
import org.grupotres.appcongreso.ui.helpers.INavigator
import org.grupotres.appcongreso.util.setOnClickListener

class LectureAdapter(private val viewModel: LectureViewModel, private val navigator: INavigator) :
	ListAdapter<Lecture, LectureAdapter.LectureViewHolder>(LectureCallback()) {

	inner class LectureViewHolder(private val binding: ItemLectureDetailBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(lecture: Lecture) {
			binding.lectureTitle.text = lecture.title
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureViewHolder {
		val binding = ItemLectureDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		val holder = LectureViewHolder(binding)
		holder.setOnClickListener { position, _ -> goToLectureDetail(position) }
		return LectureViewHolder(binding)
	}

	private fun goToLectureDetail(position: Int) {
		TODO("Falta implementar")
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