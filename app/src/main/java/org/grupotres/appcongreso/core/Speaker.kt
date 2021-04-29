package org.grupotres.appcongreso.core

data class Speaker(
	val id: String,
	val surname: String,
	val maternalSurname: String? = null,
	val name: String,
//	val birthday: String,
//	val gender: String,
	val country: String,
	val uriPhoto: String? = null
)
