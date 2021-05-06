package org.grupotres.appcongreso.data

import androidx.room.Dao
import androidx.room.Query
import org.grupotres.appcongreso.core.Speaker

@Dao
interface SpeakerDao {

	@Query("SELECT * FROM speaker")
	suspend fun getSpeakers() : List<Speaker>
}