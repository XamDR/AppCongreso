package org.grupotres.appcongreso.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.grupotres.appcongreso.MainActivity
import org.grupotres.appcongreso.R
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

val String.Companion.Empty: String
	get() = ""

fun String.toEpoch(): Long {
	val pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy K:mm a")
	val date = LocalDateTime.parse(this, pattern)
	val zone = ZoneId.of("America/Lima")
	return date.atZone(zone).toInstant().toEpochMilli()
}

fun <T : RecyclerView.ViewHolder> T.setOnClickListener(callback: (position: Int, type: Int) -> Unit): T {
	itemView.setOnClickListener {
		ViewCompat.postOnAnimationDelayed(itemView, {
			callback.invoke(bindingAdapterPosition, itemViewType)
		}, 100)
	}
	return this
}

val Fragment.mainActivity: MainActivity
	get() = requireActivity() as? MainActivity
		?: throw IllegalStateException("The activity this fragment is attached to does not extend MainActivity.")

fun View.showSnackbar(@StringRes message: Int,
                      length: Int = Snackbar.LENGTH_SHORT,
                      @ColorRes backColor: Int = R.color.accentColor,
                      @ColorRes textColor: Int = R.color.white): Snackbar {
	return Snackbar.make(this, message, length)
		.setBackgroundTint(ContextCompat.getColor(this.context, backColor))
		.setTextColor(ContextCompat.getColor(this.context, textColor))
		.also { it.show() }
}

fun debug(tag: String, msg: Any?) = android.util.Log.d(tag, msg.toString())

fun copyTextToClipboard(context: Context, text: CharSequence) {
	val manager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
	manager.setPrimaryClip(ClipData.newPlainText("código", text))
	Toast.makeText(context, "Código copiado al Portapapeles", Toast.LENGTH_SHORT).show()
}
