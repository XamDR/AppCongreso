package org.grupotres.appcongreso.util

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

fun <T : RecyclerView.ViewHolder> T.setOnClickListener(callback: (position: Int, type: Int) -> Unit): T {
	itemView.setOnClickListener {
		ViewCompat.postOnAnimationDelayed(itemView, {
			callback.invoke(adapterPosition, itemViewType)
		}, 100)
	}
	return this
}
