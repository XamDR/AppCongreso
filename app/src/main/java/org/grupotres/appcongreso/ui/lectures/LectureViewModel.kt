package org.grupotres.appcongreso.ui.lectures

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.core.Speaker
import org.grupotres.appcongreso.data.AppRepository

class LectureViewModel(private val repository: AppRepository) : ViewModel() {

	private val _lectures = MutableLiveData(mutableListOf<Lecture>())
	val lectures: LiveData<MutableList<Lecture>> = _lectures

	init {
		fetchLectures()
	}

	private fun fetchLectures() {
		viewModelScope.launch {
			_lectures.value = repository.getLectures().toMutableList()
		}
	}
}