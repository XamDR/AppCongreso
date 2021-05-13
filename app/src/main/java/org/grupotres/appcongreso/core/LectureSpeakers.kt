package org.grupotres.appcongreso.core

import androidx.room.Embedded
import androidx.room.Relation

data class LectureSpeakers(
	@Embedded val lecture: Lecture,
	@Relation(
		parentColumn = "id",
		entityColumn = "lecture_id"
	) val speakers: List<Speaker>
)
