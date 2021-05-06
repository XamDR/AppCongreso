package org.grupotres.appcongreso.data

import org.grupotres.appcongreso.core.Speaker

object SpeakerData {

	suspend fun populateData(repository: AppRepository) {
		repository.insertAllSpeakers(speakers)
	}

	private val speakers = listOf(
		Speaker(
			id = "72448162",
			surname = "Leon",
			maternalSurname = "Rojas",
			name = "Doraliz",
			birthday = "17/08/1998",
			country = "Peru",
		),
		Speaker(
			id = "74429354",
			surname = "Sotil",
			name = "Andres",
			birthday = "13/05/1990",
			country = "Peru",
		),
	)
}