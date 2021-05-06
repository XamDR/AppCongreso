package org.grupotres.appcongreso.core

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lecture(
	@PrimaryKey val id: String,
	@ColumnInfo(name = "start_time") val startTime: String,
	@ColumnInfo(name = "end_time") val endTime: String,
	val title: String,
	val url: String
)
