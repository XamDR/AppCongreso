package org.grupotres.appcongreso.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.core.LectureSpeakers
import org.grupotres.appcongreso.core.Speaker

@Dao
interface AppDao {

	@Query("SELECT * FROM lecture")
	suspend fun getLectures() : List<Lecture>

	@Transaction
	@Query("SELECT * FROM lecture")
	suspend fun getLecturesWithSpeakers(): List<LectureSpeakers>

	@Query("SELECT * FROM Speaker")
	suspend fun getSpeakers() : List<Speaker>
}
