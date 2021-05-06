package org.grupotres.appcongreso.ui.speakers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.grupotres.appcongreso.core.Speaker
import org.grupotres.appcongreso.data.AppRepository

class SpeakerViewModel(private val repository: AppRepository) : ViewModel() {

	private val _speakers = mutableListOf<Speaker>()
	val speakers: List<Speaker> = _speakers

	init {
		fetchSpeakers()
	}

	private fun fetchSpeakers() {
		viewModelScope.launch {
			repository.getSpeakers()
		}
	}
}