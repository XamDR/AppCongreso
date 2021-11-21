package org.grupotres.appcongreso.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import edu.icontinental.congresoi40.R
import org.grupotres.appcongreso.util.setNightMode

class SettingsFragment : PreferenceFragmentCompat() {

	private val themeListener = SharedPreferences.OnSharedPreferenceChangeListener { preferences, key ->
		when (key) {
			"app_theme" -> setNightMode(preferences)
		}
	}

	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		setPreferencesFromResource(R.xml.root_preferences, rootKey)
	}

	override fun onPause() {
		super.onPause()
		preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(themeListener)
	}

	override fun onResume() {
		super.onResume()
		preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(themeListener)
	}
}