package org.grupotres.appcongreso.core

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
	foreignKeys = [ForeignKey(
		entity = Lecture::class,
		parentColumns = arrayOf("id"),
		childColumns = arrayOf("lecture_id"),
		onDelete = ForeignKey.NO_ACTION
	)]
)
data class Resource(
	val id : String,
	val name: String,
	val uri: String,
	@ColumnInfo(name = "lecture_id", index = true) val lectureId: String
)