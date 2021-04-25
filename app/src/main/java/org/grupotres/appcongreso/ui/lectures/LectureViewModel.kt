package org.grupotres.appcongreso.ui.lectures

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LectureViewModel : ViewModel() {

	private val _text = MutableLiveData("This is Lecture list Fragment")
	val text: LiveData<String> = _text
}