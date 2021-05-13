package org.grupotres.appcongreso.ui.lectures

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.grupotres.appcongreso.core.LectureSpeakers
import org.grupotres.appcongreso.databinding.ItemLectureDetailBinding
import org.grupotres.appcongreso.ui.helpers.INavigator
import org.grupotres.appcongreso.util.setOnClickListener

class LectureAdapter(private val viewModel: LectureViewModel, private val navigator: INavigator) :
	ListAdapter<LectureSpeakers, LectureAdapter.LectureViewHolder>(LectureCallback()) {

	inner class LectureViewHolder(private val binding: ItemLectureDetailBinding) :
		RecyclerView.ViewHolder(binding.root) {

		@SuppressLint("SetTextI18n")
		fun bind(lectureSpeaker: LectureSpeakers) {
			binding.lectureTitle.text = lectureSpeaker.lecture.title
			binding.lectureDate.text = "Fecha Inicio: ${lectureSpeaker.lecture.startTime} " +
					"Fecha Fin: ${lectureSpeaker.lecture.endTime}"
			binding.lectureDesc.text = "Enlace ${lectureSpeaker.lecture.url}"
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureViewHolder {
		val binding = ItemLectureDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		val holder = LectureViewHolder(binding)
		holder.setOnClickListener { position, _ -> goToLectureDetail(position) }
		return holder
	}

	private fun goToLectureDetail(position: Int) {
		val lectureSpeaker = getItem(position)
		val navDirections = LectureListFragmentDirections.actionNavLectureListToLectureDetail(lectureSpeaker)
		navigator.navigate(navDirections)
	}

	override fun onBindViewHolder(holder: LectureViewHolder, position: Int) {
		val lecture = viewModel.lectures.value?.get(position)
		lecture?.let { holder.bind(it) }
	}

	class LectureCallback : DiffUtil.ItemCallback<LectureSpeakers>() {

		override fun areItemsTheSame(oldLecture: LectureSpeakers, newLecture: LectureSpeakers)
			= oldLecture.lecture.id == newLecture.lecture.id

		override fun areContentsTheSame(oldLecture: LectureSpeakers, newLecture: LectureSpeakers)
			= oldLecture == newLecture
	}
}