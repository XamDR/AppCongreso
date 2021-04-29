package org.grupotres.appcongreso.ui.speakers

import androidx.lifecycle.ViewModel
import org.grupotres.appcongreso.core.Speaker
import java.util.UUID

class SpeakerViewModel : ViewModel() {

	private val _speakers = mutableListOf<Speaker>()
	val speakers: List<Speaker> = _speakers

	init {
		fetchSpeakers()
	}

	private fun fetchSpeakers() {
		_speakers.add(
			Speaker(id = UUID.randomUUID().toString(), surname = "Macera", name = "Margarita", country = "Belgica"))
		_speakers.add(
			Speaker(id = UUID.randomUUID().toString(), surname = "Victorero", name = "Felipe", country = "Chile"))
		_speakers.add(
			Speaker(id = UUID.randomUUID().toString(), surname = "Sotil", name = "Andres", country = "Perú"))
		_speakers.add(
			Speaker(id = UUID.randomUUID().toString(), surname = "Marriaga", name = "Angie", country = "Colombia"))
		_speakers.add(
			Speaker(id = UUID.randomUUID().toString(), surname = "Aguillon", name = "David", country = "México"))
	}
}