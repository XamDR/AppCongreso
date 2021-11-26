package org.grupotres.appcongreso.core

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.grupotres.appcongreso.util.Empty
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

@Parcelize
data class Lecture(
	val id: String = String.Empty,
	val title: String = String.Empty,
	val startTime: Date = Date.from(Instant.now()),
	val endTime: Date = Date.from(Instant.now()),
	val capacity: Int = 100,
	val description: String = String.Empty,
	val topic: String = String.Empty,
	val url: String = String.Empty,
	val room: String = String.Empty,
	val day: String = String.Empty,
	val isHeader: Boolean = false,
	val surnameSpeaker: String = String.Empty,
	val maternalSurnameSpeaker: String = String.Empty,
	val nameSpeaker: String = String.Empty,
	val countrySpeaker: String = String.Empty,
	val companySpeaker: String = String.Empty,
	val academicInfoSpeaker: String = String.Empty,
	val uriPhotoSpeaker: String = String.Empty): Parcelable {

	@IgnoredOnParcel
	val resources: List<String>? = null

	fun getDate(): String {
		val startTimeWithoutDate = SimpleDateFormat("HH:mm", Locale.getDefault()).format(startTime)
		val endTimeWithoutDate = SimpleDateFormat("HH:mm", Locale.getDefault()).format(endTime)
		return "$startTimeWithoutDate – $endTimeWithoutDate"
	}
}
