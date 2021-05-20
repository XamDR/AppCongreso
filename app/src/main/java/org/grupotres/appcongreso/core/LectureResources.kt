package org.grupotres.appcongreso.core

import androidx.room.Embedded
import androidx.room.Relation

data class LectureResources(
	@Embedded val lecture: Lecture,
	@Relation(
		parentColumn = "id",
		entityColumn = "lecture_id"
	) val resources: List<Resource>
)
