package org.grupotres.appcongreso.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.WelcomeActivity
import org.grupotres.appcongreso.databinding.FragmentInfoAppBinding

class InfoAppFragment : Fragment() {

	private var binding: FragmentInfoAppBinding? = null

	override fun onCreateView(inflater: LayoutInflater,
	                          container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		binding = FragmentInfoAppBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val summary = getString(R.string.features)
		binding?.appFeatures?.text = HtmlCompat.fromHtml(summary, HtmlCompat.FROM_HTML_MODE_COMPACT)
		binding?.next?.setOnClickListener { (activity as WelcomeActivity).goToMainActivity() }
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}
}