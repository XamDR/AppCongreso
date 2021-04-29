package org.grupotres.appcongreso.ui.helpers

import android.os.Bundle

interface INavigator {
	fun navigate(resId: Int, bundle: Bundle? = null)
}