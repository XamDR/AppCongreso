package org.grupotres.appcongreso.ui.settings

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.google.android.material.color.MaterialColors
import org.grupotres.appcongreso.R

class ColourizedTextPreference @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null
): Preference(context, attrs) {

	override fun onBindViewHolder(holder: PreferenceViewHolder) {
		super.onBindViewHolder(holder)
		val textView = holder.findViewById(android.R.id.title) as TextView
		val color = MaterialColors.getColor(textView, R.attr.colourizedTextPreferenceColor)
		textView.setTextColor(color)
	}
}