package org.grupotres.appcongreso.ui.lectures

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.core.Speaker
import org.grupotres.appcongreso.databinding.ItemLectureDetailBinding
import org.grupotres.appcongreso.ui.helpers.INavigator
import org.grupotres.appcongreso.ui.speakers.SpeakerListFragmentDirections
import org.grupotres.appcongreso.util.setOnClickListener

class LectureAdapter(private val viewModel: LectureViewModel, private val navigator: INavigator) :
	ListAdapter<Lecture, LectureAdapter.LectureViewHolder>(LectureCallback()) {

	inner class LectureViewHolder(private val binding: ItemLectureDetailBinding) :
		RecyclerView.ViewHolder(binding.root) {

		@SuppressLint("SetTextI18n")
		fun bind(lecture: Lecture) {
			binding.lectureTitle.text = lecture.title
			binding.lectureDate.text = "Fecha Inicio: ${lecture.startTime} Fecha Fin: ${lecture.endTime}"
			binding.lectureDesc.text = "Enlace ${lecture.url}"
		}



	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureViewHolder {
		val binding = ItemLectureDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		val holder = LectureViewHolder(binding)
		holder.setOnClickListener { position, _ -> goToLectureDetail(position) }
		return holder
	}

	private fun goToLectureDetail(position: Int) {
		val lecture = getItem(position)
		val navDirections = LectureListFragmentDirections.actionNavLectureListToLectureDetail(lecture)
		navigator.navigate(navDirections)
	}

	override fun onBindViewHolder(holder: LectureViewHolder, position: Int) {
		val lecture = viewModel.lectures.value?.get(position)
		lecture?.let { holder.bind(it) }
	}

	class LectureCallback : DiffUtil.ItemCallback<Lecture>() {

		override fun areItemsTheSame(oldLecture: Lecture, newLecture: Lecture) = oldLecture.id == newLecture.id

		override fun areContentsTheSame(oldLecture: Lecture, newLecture: Lecture) = oldLecture == newLecture
	}
}