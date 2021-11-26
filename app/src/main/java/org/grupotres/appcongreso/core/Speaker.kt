package org.grupotres.appcongreso.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Speaker(
	val surname: String,
	val maternalSurname: String,
	val name: String,
	val country: String,
	val company: String,
	val academicInfo: String,
	val uriPhoto: String) : Parcelable
