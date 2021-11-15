package org.grupotres.appcongreso.ui.lectures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.icontinental.congresoi40.databinding.ItemLectureBinding
import edu.icontinental.congresoi40.databinding.LectureListHeaderBinding
import org.grupotres.appcongreso.core.LectureSpeakers
import org.grupotres.appcongreso.ui.helpers.INavigator
import org.grupotres.appcongreso.util.setOnClickListener

class LectureAdapter(private val navigator: INavigator) :
	ListAdapter<LectureSpeakers, BaseViewHolder>(LectureCallback()) {

	inner class LectureViewHolder(private val binding: ItemLectureBinding) : BaseViewHolder(binding) {

		override fun bind(lectureSpeaker: LectureSpeakers) {
			binding.lectureTitle.text = lectureSpeaker.lecture.title
			binding.lectureTime.text = lectureSpeaker.lecture.getDate()
		}
	}

	inner class HeaderViewHolder(private val binding: LectureListHeaderBinding) : BaseViewHolder(binding) {

		override fun bind(lectureSpeaker: LectureSpeakers) {
			binding.headerTitle.text = lectureSpeaker.lecture.title
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
		return if (viewType == HEADER_VIEW) {
			val binding = LectureListHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			return HeaderViewHolder(binding)
		}
		else {
			val binding = ItemLectureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			val holder = LectureViewHolder(binding)
			holder.setOnClickListener { position, _ -> goToLectureDetail(position) }
		}
	}

	override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
		val lecture = getItem(position)
		lecture?.let { holder.bind(it) }
	}

	override fun getItemViewType(position: Int) = if (getItem(position).lecture.isHeader) HEADER_VIEW else ITEM_VIEW

	override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
		super.onAttachedToRecyclerView(recyclerView)
		recyclerView.addItemDecoration(StickyHeaderDecoration(recyclerView) { position ->
			getItemViewType(position) == HEADER_VIEW
		})
	}

	private fun goToLectureDetail(position: Int) {
		val lectureSpeaker = getItem(position)

		if (lectureSpeaker.speakers.isNotEmpty()) {
			val speaker = lectureSpeaker.speakers[0]
			val navDirections = LectureListFragmentDirections.actionNavLectureListToLectureDetail(lectureSpeaker, speaker)
			navigator.navigate(navDirections)
		}
	}

	class LectureCallback : DiffUtil.ItemCallback<LectureSpeakers>() {

		override fun areItemsTheSame(oldLecture: LectureSpeakers, newLecture: LectureSpeakers)
			= oldLecture.lecture.id == newLecture.lecture.id

		override fun areContentsTheSame(oldLecture: LectureSpeakers, newLecture: LectureSpeakers)
			= oldLecture == newLecture
	}

	companion object {
		const val HEADER_VIEW = 0
		const val ITEM_VIEW = 1
	}
}