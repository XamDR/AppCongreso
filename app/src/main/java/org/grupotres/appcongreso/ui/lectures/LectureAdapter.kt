package org.grupotres.appcongreso.ui.lectures

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.icontinental.congresoi40.databinding.ItemLectureBinding
import edu.icontinental.congresoi40.databinding.LectureListHeaderBinding
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.core.Speaker
import org.grupotres.appcongreso.ui.helpers.INavigator
import org.grupotres.appcongreso.util.setOnClickListener

class LectureAdapter(private val navigator: INavigator) :
	ListAdapter<Lecture, BaseViewHolder>(LectureCallback()) {

	private val colors = mapOf(
		"Construcción" to "#0000FF",
		"Ingeniería" to "#00FF00",
		"Minería" to "#FF0000",
		"Big Data" to "#800080",
		"IoT" to "#FF00FF",
		"Seguridad Informática" to "#FF4500",
		"Estructuras" to "#FFFF00",
		"Ecotoxicología" to "#008080",
		"Informática" to "#00FFFF",
		"Geología" to "#8B0000",
	)

	inner class LectureViewHolder(private val binding: ItemLectureBinding) : BaseViewHolder(binding) {

		override fun bind(lecture: Lecture) {
			binding.lectureTitle.text = lecture.title
			binding.lectureTime.text = lecture.getDate()
			binding.lectureTopic.text = lecture.topic
			val color = Color.parseColor(colors[lecture.topic])
			DrawableCompat.setTint(binding.lectureTopic.chipIcon!!, color)
		}
	}

	inner class HeaderViewHolder(private val binding: LectureListHeaderBinding) : BaseViewHolder(binding) {

		override fun bind(lecture: Lecture) {
			binding.headerTitle.text = lecture.title
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

	override fun getItemViewType(position: Int) = if (getItem(position).isHeader) HEADER_VIEW else ITEM_VIEW

	override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
		super.onAttachedToRecyclerView(recyclerView)
		recyclerView.addItemDecoration(StickyHeaderDecoration(recyclerView) { position ->
			getItemViewType(position) == HEADER_VIEW
		})
	}

	private fun goToLectureDetail(position: Int) {
		val lecture = getItem(position)

		if (!lecture.isHeader) {
			val speaker = Speaker(
				surname = lecture.surnameSpeaker,
				maternalSurname = lecture.maternalSurnameSpeaker,
				name = lecture.nameSpeaker,
				country = lecture.countrySpeaker,
				company = lecture.companySpeaker,
				academicInfo = lecture.academicInfoSpeaker,
				uriPhoto = lecture.uriPhotoSpeaker
			)
			val navDirections = LectureListFragmentDirections.actionNavLectureListToLectureDetail(lecture, speaker)
			navigator.navigate(navDirections)
		}
	}

	class LectureCallback : DiffUtil.ItemCallback<Lecture>() {

		override fun areItemsTheSame(oldLecture: Lecture, newLecture: Lecture)
			= oldLecture.id == newLecture.id

		override fun areContentsTheSame(oldLecture: Lecture, newLecture: Lecture)
			= oldLecture == newLecture
	}

	companion object {
		const val HEADER_VIEW = 0
		const val ITEM_VIEW = 1
	}
}