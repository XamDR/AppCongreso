package org.grupotres.appcongreso.ui.settings

import android.content.Context
import android.util.AttributeSet
import androidx.core.text.HtmlCompat
import androidx.preference.Preference
import org.grupotres.appcongreso.R

class LongSummaryPreference @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null
): Preference(context, attrs) {

	override fun getSummary(): CharSequence {
		val summary = context.getString(R.string.devs)
		return HtmlCompat.fromHtml(summary, HtmlCompat.FROM_HTML_MODE_COMPACT)
	}
}