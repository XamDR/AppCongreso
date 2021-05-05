package org.grupotres.appcongreso

import android.app.Application
import org.grupotres.appcongreso.data.AppRepository

@Suppress("unused")
class App : Application() {

	override fun onCreate() {
		super.onCreate()
		AppRepository.initialize(this)
	}
}