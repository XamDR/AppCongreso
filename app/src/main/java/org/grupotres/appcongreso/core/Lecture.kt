package org.grupotres.appcongreso.core

import android.os.Parcelable
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
	val speaker: Speaker? = null,
	val isHeader: Boolean = false): Parcelable {

	fun getDate(): String {
		val startTimeWithoutDate = SimpleDateFormat("HH:mm", Locale.getDefault()).format(startTime)
		val endTimeWithoutDate = SimpleDateFormat("HH:mm", Locale.getDefault()).format(endTime)
		return "$startTimeWithoutDate – $endTimeWithoutDate"
	}
}
