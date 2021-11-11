package org.grupotres.appcongreso.ui.settings

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.util.setNightMode

class SettingsFragment : PreferenceFragmentCompat() {

	private val themeListener = SharedPreferences.OnSharedPreferenceChangeListener { preferences, key ->
		when (key) {
			"app_theme" -> setNightMode(preferences)
		}
	}

	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		setPreferencesFromResource(R.xml.root_preferences, rootKey)
		findPreference<Preference>("tos_privacy")?.setOnPreferenceClickListener {
			goToWebsite(); true
		}
	}

	override fun onPause() {
		super.onPause()
		preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(themeListener)
	}

	override fun onResume() {
		super.onResume()
		preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(themeListener)
	}

	private fun goToWebsite() {
		startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(UCCI_URI)))
	}

	companion object {
		private const val UCCI_URI = "https://ucontinental.edu.pe/"
	}
}