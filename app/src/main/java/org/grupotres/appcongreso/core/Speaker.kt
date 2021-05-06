package org.grupotres.appcongreso.core

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Speaker(
	@PrimaryKey val id: String,
	val surname: String,
	@ColumnInfo(name = "maternal_surname") val maternalSurname: String? = null,
	val name: String,
	val birthday: String,
	val gender: String,
	val country: String,
	@ColumnInfo(name = "uri_photo") val uriPhoto: String? = null
)
