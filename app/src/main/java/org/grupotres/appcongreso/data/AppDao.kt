package org.grupotres.appcongreso.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import org.grupotres.appcongreso.core.LectureSpeakers

@Dao
interface AppDao {
	@Transaction
	@Query("SELECT * FROM lecture WHERE sala IN (:rooms)")
	fun getLecturesWithSpeakersByRoom(vararg rooms: String) : Flow<List<LectureSpeakers>>
}
