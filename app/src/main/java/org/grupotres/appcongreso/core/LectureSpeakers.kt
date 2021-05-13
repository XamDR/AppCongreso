package org.grupotres.appcongreso.core

import androidx.room.Embedded
import androidx.room.Relation

data class LectureSpeakers(
	@Embedded val lecture: Lecture,
	@Relation(
		parentColumn = "id",
		entityColumn = "lectureId"
	)
	val speakers: List<Speaker>
)
