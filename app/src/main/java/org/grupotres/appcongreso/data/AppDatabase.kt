package org.grupotres.appcongreso.data

import androidx.room.RoomDatabase


abstract class AppDatabase : RoomDatabase() {

	abstract fun lectureDao(): LectureDao

	abstract fun speakerDao(): SpeakerDao
}