package org.grupotres.appcongreso.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import org.grupotres.appcongreso.R

class SettingsFragment : PreferenceFragmentCompat() {

	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		setPreferencesFromResource(R.xml.root_preferences, rootKey)
	}
}