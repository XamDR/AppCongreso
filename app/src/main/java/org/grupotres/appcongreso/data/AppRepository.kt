package org.grupotres.appcongreso.data

import android.content.Context
import androidx.room.Room
import org.grupotres.appcongreso.core.Speaker

class AppRepository(context: Context) {

	suspend fun getLectures() = lectureDao.getLectures()

	suspend fun getSpeakers() = speakerDao.getSpeakers()

	suspend fun insertAllSpeakers(speakers: List<Speaker>) = speakerDao.insertAllSpeakers(speakers)

	private val dbName = "app-database"

	private val database = Room
		.databaseBuilder(context.applicationContext, AppDatabase::class.java, dbName)
		.build()

	private val lectureDao = database.lectureDao()

	private val speakerDao = database.speakerDao()

	companion object {
		val Instance: AppRepository
			get() = instance ?: throw IllegalStateException("Repository service must be initialized")
		private var instance: AppRepository? = null

		fun initialize(context: Context) {
			if (instance == null) {
				instance = AppRepository(context)
			}
		}
	}
}