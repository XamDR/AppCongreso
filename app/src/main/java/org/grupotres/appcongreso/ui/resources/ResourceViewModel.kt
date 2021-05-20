package org.grupotres.appcongreso.ui.resources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.core.LectureResources
import org.grupotres.appcongreso.core.Resource

class ResourceViewModel : ViewModel() {

	private val _resources = MutableLiveData(mutableListOf<LectureResources>())
	val resources: LiveData<MutableList<LectureResources>> = _resources

	init {
		fechResources()
	}

	private fun fechResources() {
		_resources.value?.add(LectureResources(Lecture("1", "", "", "DEMO", ""),
			listOf(Resource("1", "EJEMPLO", "", "1"))))
	}
}