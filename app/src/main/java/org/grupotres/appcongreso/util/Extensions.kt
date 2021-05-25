package org.grupotres.appcongreso.util

import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.grupotres.appcongreso.MainActivity
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
