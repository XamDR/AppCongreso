package org.grupotres.appcongreso.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

	private val _text = MutableLiveData("This is login Fragment")
	val text: LiveData<String> = _text
}