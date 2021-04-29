package org.grupotres.appcongreso.ui.lectures

import androidx.lifecycle.ViewModel
import org.grupotres.appcongreso.core.Lecture
import java.util.UUID

class LectureViewModel : ViewModel() {

	private val _lectures = mutableListOf<Lecture>()
	val lectures = _lectures

	init {
		fetchLectures()
	}

	private fun fetchLectures() {
		lectures.add(Lecture(id = UUID.randomUUID().toString(), title = "La informática en el desarrollo minero"))
		lectures.add(Lecture(id = UUID.randomUUID().toString(), title = "Aplicaciones del Big Data"))
		lectures.add(Lecture(id = UUID.randomUUID().toString(), title = "Seguridad informática en el 2021"))
		lectures.add(Lecture(id = UUID.randomUUID().toString(), title = "Sobre la popularidad del paradigma de la programación funcional"))
	}
}