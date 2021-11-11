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
	val country: String? = null,
	val company: String? = null,
	@ColumnInfo(name = "academic_info") val academicInfo: String? = null,
	@ColumnInfo(name = "uri_photo") val uriPhoto: String? = null,
	@ColumnInfo(name = "lecture_id", index = true) val lectureId: String
) : Parcelable {

	override fun toString() = "$name $surname $maternalSurname"
}
