package org.grupotres.appcongreso.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.grupotres.appcongreso.core.Speaker

@Dao
interface SpeakerDao {

	@Query("SELECT * FROM speaker")
	suspend fun getSpeakers() : List<Speaker>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertAllSpeakers(speakers: List<Speaker>)
}