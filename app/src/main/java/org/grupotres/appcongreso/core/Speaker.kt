package org.grupotres.appcongreso.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.grupotres.appcongreso.util.Empty

@Parcelize
data class Speaker(
	val id: String = String.Empty,
	val surname: String = String.Empty,
	val maternalSurname: String = String.Empty,
	val name: String = String.Empty,
	val country: String = String.Empty,
	val company: String = String.Empty,
	val academicInfo: String = String.Empty,
	val uriPhoto: String = String.Empty) : Parcelable {

	override fun toString() = "$name $surname $maternalSurname"
}
