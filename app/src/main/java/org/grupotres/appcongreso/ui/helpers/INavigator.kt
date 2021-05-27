package org.grupotres.appcongreso.ui.helpers

import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator

interface INavigator {
	fun navigate(navDirections: NavDirections, extras: FragmentNavigator.Extras? = null)
}