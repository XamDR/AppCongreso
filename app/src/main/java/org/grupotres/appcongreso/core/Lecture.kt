package org.grupotres.appcongreso.core

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.grupotres.appcongreso.util.Empty

@Parcelize
@Entity
data class Lecture(
	@PrimaryKey val id: String = String.Empty,
	val title: String = String.Empty,
	@ColumnInfo(name = "start_time") val startTime: String = String.Empty,
	@ColumnInfo(name = "end_time") val endTime: String = String.Empty,
	val description: String = String.Empty,
	val topic: String = String.Empty,
	val url: String = String.Empty
) : Parcelable {

	@IgnoredOnParcel
	@Ignore val resources: List<String>? = null
}
