package org.grupotres.appcongreso.ui.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.grupotres.appcongreso.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {
	private var binding: FragmentCalendarBinding? = null

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?

	): View? {
		binding = FragmentCalendarBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

}