package org.grupotres.appcongreso.ui.speakers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.grupotres.appcongreso.data.AppRepository

class SpeakerViewModelFactory(private val repository: AppRepository): ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>) = SpeakerViewModel(repository) as T
}