package org.grupotres.appcongreso.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingStateAdapter(
	fragment: Fragment,
	private val infoFragments: List<Fragment>) : FragmentStateAdapter(fragment) {

	override fun createFragment(position: Int) = infoFragments[position]

	override fun getItemCount() = infoFragments.size
}