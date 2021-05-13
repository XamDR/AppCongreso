package org.grupotres.appcongreso.core

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
	foreignKeys = [ForeignKey(
		entity = Lecture::class,
		parentColumns = arrayOf("id"),
		childColumns = arrayOf("lecture_id"),
		onDelete = ForeignKey.NO_ACTION
	)]
)
data class Speaker(
	@PrimaryKey val id: String,
	val surname: String,
	@ColumnInfo(name = "maternal_surname") val maternalSurname: String? = null,
	val name: String,
	val country: String,
	val info: String,
	@ColumnInfo(name = "uri_photo") val uriPhoto: String? = null,
	@ColumnInfo(name = "lecture_id", index = true) val lectureId: String
) : Parcelable
