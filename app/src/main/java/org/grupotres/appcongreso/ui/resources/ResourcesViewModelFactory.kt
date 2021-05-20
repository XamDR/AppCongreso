package org.grupotres.appcongreso.ui.resources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.grupotres.appcongreso.data.AppRepository
import org.grupotres.appcongreso.ui.lectures.LectureViewModel

class ResourcesViewModelFactory(private val repository: AppRepository): ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>) = ResourcesViewModel(repository) as T
}