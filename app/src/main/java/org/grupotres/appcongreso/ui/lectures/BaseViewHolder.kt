package org.grupotres.appcongreso.ui.lectures

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import org.grupotres.appcongreso.core.Lecture

abstract class BaseViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

	abstract fun bind(lecture: Lecture)
}