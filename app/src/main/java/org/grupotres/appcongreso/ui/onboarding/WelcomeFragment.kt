package org.grupotres.appcongreso.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.grupotres.appcongreso.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

	private var binding: FragmentWelcomeBinding? = null

	override fun onCreateView(inflater: LayoutInflater,
	                          container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		binding = FragmentWelcomeBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}
}