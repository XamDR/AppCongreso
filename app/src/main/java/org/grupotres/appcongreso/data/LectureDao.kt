package org.grupotres.appcongreso.data

import androidx.room.Query
import org.grupotres.appcongreso.core.Lecture

interface LectureDao {

	//@Query("SELECT * FROM note")
	suspend fun getLectures() : List<Lecture>
}