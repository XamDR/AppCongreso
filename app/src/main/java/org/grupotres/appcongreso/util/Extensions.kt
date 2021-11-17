package org.grupotres.appcongreso.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.text.SpannableString
import android.text.style.BulletSpan
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import edu.icontinental.congresoi40.R
import org.grupotres.appcongreso.MainActivity

val String.Companion.Empty: String
	get() = ""

//fun String.toEpoch(): Long {
//	val pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy K:mm a")
//	val date = LocalDateTime.parse(this, pattern)
//	val zone = ZoneId.of("America/Lima")
//	return date.atZone(zone).toInstant().toEpochMilli()
//}

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

inline fun View.doOnEachNextLayout(crossinline action: (view: View) -> Unit) {
	addOnLayoutChangeListener { view, _, _, _, _, _, _, _, _ ->
		action(
			view
		)
	}
}

@Suppress("deprecation")
fun isNetworkAvailable(context: Context): Boolean {
	val connectivityManager =
		context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
	val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
	return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun List<String>.toBulletedList(): CharSequence {
	return SpannableString(this.joinToString("\n")).apply {
		this@toBulletedList.foldIndexed(0) { index, acc, span ->
			val end = acc + span.length + if (index != this@toBulletedList.size - 1) 1 else 0
			this.setSpan(BulletSpan(16), acc, end, 0)
			end
		}
	}
}