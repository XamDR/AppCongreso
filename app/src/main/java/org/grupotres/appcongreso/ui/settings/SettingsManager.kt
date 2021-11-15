package org.grupotres.appcongreso.ui.settings

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import org.grupotres.appcongreso.util.Empty

class SettingsManager(context: Context) {

	private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

	var userAvatarPath = preferences.getString(USER_AVATAR_PATH, String.Empty)
		set(value) = preferences.edit { putString(USER_AVATAR_PATH, value) }

	companion object {
		private const val USER_AVATAR_PATH = "user_avatar_path"
	}
}