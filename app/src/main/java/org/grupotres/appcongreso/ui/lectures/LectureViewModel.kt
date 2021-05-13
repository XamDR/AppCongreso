package org.grupotres.appcongreso.ui.lectures

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.grupotres.appcongreso.core.LectureSpeakers
import org.grupotres.appcongreso.data.AppRepository

class LectureViewModel(private val repository: AppRepository) : ViewModel() {

	private val _lectures = MutableLiveData(mutableListOf<LectureSpeakers>())
	val lectures: LiveData<MutableList<LectureSpeakers>> = _lectures

	init {
		fetchLectures()
	}

	private fun fetchLectures() {
		viewModelScope.launch {
			_lectures.value = repository.getLectureWithSpeakers().toMutableList()
		}
	}
}