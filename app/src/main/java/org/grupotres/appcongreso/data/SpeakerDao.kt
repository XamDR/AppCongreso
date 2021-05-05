package org.grupotres.appcongreso.data

import androidx.room.Query
import org.grupotres.appcongreso.core.Speaker

interface SpeakerDao {

	//@Query("SELECT * FROM Speaker")
	suspend fun getSpeakers() : List<Speaker>
}