package org.grupotres.appcongreso.ui.speakers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SpeakerViewModel : ViewModel() {

	private val _text = MutableLiveData("This is Speaker list Fragment")
	val text: LiveData<String> = _text
}