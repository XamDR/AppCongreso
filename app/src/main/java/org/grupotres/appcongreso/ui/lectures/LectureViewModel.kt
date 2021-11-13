package org.grupotres.appcongreso.ui.lectures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import org.grupotres.appcongreso.data.AppRepository

class LectureViewModel(private val repository: AppRepository) : ViewModel() {

	fun fetchLecturesByRoom(vararg rooms: String) = repository.getLectureWithSpeakersByRoom(*rooms).asLiveData()
}