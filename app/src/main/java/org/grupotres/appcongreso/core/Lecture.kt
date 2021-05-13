package org.grupotres.appcongreso.core

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Lecture(
	@PrimaryKey val id: String,
	@ColumnInfo(name = "start_time") val startTime: String,
	@ColumnInfo(name = "end_time") val endTime: String,
	val title: String,
	val url: String
) : Parcelable
