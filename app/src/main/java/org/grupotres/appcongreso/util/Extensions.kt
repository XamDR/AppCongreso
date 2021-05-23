package org.grupotres.appcongreso.util

import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.grupotres.appcongreso.MainActivity

val String.Companion.Empty: String
	get() = ""

fun <T : RecyclerView.ViewHolder> T.setOnClickListener(callback: (position: Int, type: Int) -> Unit): T {
	itemView.setOnClickListener {
		ViewCompat.postOnAnimationDelayed(itemView, {
			callback.invoke(adapterPosition, itemViewType)
		}, 100)
	}
	return this
}

val Fragment.mainActivity: MainActivity
	get() = requireActivity() as? MainActivity
		?: throw IllegalStateException("The activity this fragment is attached to does not extend MainActivity.")