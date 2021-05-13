package org.grupotres.appcongreso.core

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Speaker(
	@PrimaryKey val id: String,
	val surname: String,
	@ColumnInfo(name = "maternal_surname") val maternalSurname: String? = null,
	val name: String,
	val country: String,
	val info: String,
	@ColumnInfo(name = "uri_photo") val uriPhoto: String? = null,
	val lectureId: String
) : Parcelable
