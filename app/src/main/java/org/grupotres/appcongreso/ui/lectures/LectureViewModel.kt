package org.grupotres.appcongreso.ui.lectures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.data.AppRepository

class LectureViewModel(private val repository: AppRepository) : ViewModel() {

	private val _lectures = mutableListOf<Lecture>()
	val lectures = _lectures

	init {
		fetchLectures()
	}

	private fun fetchLectures() {
		viewModelScope.launch {
			repository.getLectures()
		}
	}
}