package org.grupotres.appcongreso.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import org.grupotres.appcongreso.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

	private var binding: FragmentOnboardingBinding? = null
	private lateinit var adapter: OnboardingStateAdapter

	override fun onCreateView(inflater: LayoutInflater,
	                          container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		binding = FragmentOnboardingBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupViewPagerWithTabLayout()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	private fun setupViewPagerWithTabLayout() {
		adapter = OnboardingStateAdapter(this, listOf(WelcomeFragment(), InfoAppFragment()))
		binding?.onboardingPager?.adapter = adapter
		TabLayoutMediator(binding?.tabDots!!, binding?.onboardingPager!!) { _, _ -> }.attach()
	}
}