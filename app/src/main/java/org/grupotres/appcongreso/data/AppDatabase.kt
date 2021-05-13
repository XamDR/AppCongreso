package org.grupotres.appcongreso.data

import androidx.room.Database
import androidx.room.RoomDatabase
import org.grupotres.appcongreso.core.Lecture
import org.grupotres.appcongreso.core.Speaker

@Database(entities = [Lecture::class, Speaker::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

	abstract fun appDao(): AppDao
}