package org.grupotres.appcongreso.data

import android.content.Context
import androidx.room.Room

class AppRepository(context: Context) {

	fun getLectureWithSpeakersByRoom(vararg rooms: String) = appDao.getLecturesWithSpeakersByRoom(*rooms)

	private val dbName = "app-database"

	private val database = Room
		.databaseBuilder(context.applicationContext, AppDatabase::class.java, dbName)
		.createFromAsset("appcongreso.db")
		.fallbackToDestructiveMigration()
		.build()

	private val appDao = database.appDao()

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