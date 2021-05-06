package org.grupotres.appcongreso.data

import androidx.room.Dao
import androidx.room.Query
import org.grupotres.appcongreso.core.Lecture

@Dao
interface LectureDao {

	@Query("SELECT * FROM lecture")
	suspend fun getLectures() : List<Lecture>
}