package org.grupotres.appcongreso.data

import androidx.room.Dao
import androidx.room.Query
import org.grupotres.appcongreso.core.Speaker

@Dao
interface SpeakerDao {

	@Query("SELECT * FROM Speaker")
	suspend fun getSpeakers() : List<Speaker>
}