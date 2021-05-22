package org.grupotres.appcongreso.core

import org.grupotres.appcongreso.util.Empty

data class Resource(
	val id : String = String.Empty,
	val name: String = String.Empty,
	val url: String = String.Empty,
	val lectureId: String = String.Empty
)