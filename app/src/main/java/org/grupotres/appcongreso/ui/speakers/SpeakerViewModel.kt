package org.grupotres.appcongreso.ui.speakers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.grupotres.appcongreso.core.Speaker
import org.grupotres.appcongreso.data.AppRepository

class SpeakerViewModel(private val repository: AppRepository) : ViewModel() {

	private val _speakers = MutableLiveData(mutableListOf<Speaker>())
	val speakers: LiveData<MutableList<Speaker>> = _speakers

	init {
		fetchSpeakers()
	}

	private fun fetchSpeakers() {
		viewModelScope.launch {
			_speakers.value = repository.getSpeakers().toMutableList()
		}
	}
}