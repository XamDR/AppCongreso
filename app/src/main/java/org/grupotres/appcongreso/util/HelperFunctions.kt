package org.grupotres.appcongreso.util

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

fun setNightMode(preferences: SharedPreferences) {
	when (preferences.getString("app_theme", "-1")?.toInt()) {
		0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
		1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
		-1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
	}
}