package org.grupotres.appcongreso.ui.lectures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.grupotres.appcongreso.data.AppRepository

class LectureViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>) = LectureViewModel(repository) as T
}