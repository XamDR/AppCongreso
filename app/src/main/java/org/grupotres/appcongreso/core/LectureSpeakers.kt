package org.grupotres.appcongreso.core

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class LectureSpeakers(
	@Embedded val lecture: Lecture,
	@Relation(
		parentColumn = "id",
		entityColumn = "lecture_id"
	) val speakers: List<Speaker>
) : Parcelable
