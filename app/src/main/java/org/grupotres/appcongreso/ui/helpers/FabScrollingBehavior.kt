package org.grupotres.appcongreso.ui.helpers

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

// Taken from:
// https://github.com/XamDR/EzNotes/blob/main/app/src/main/kotlin/com/maxdr/eznotes/ui/notes/FabScrollingBehavior.kt
@Suppress("UNUSED_PARAMETER")
class FabScrollingBehavior(context: Context?, attrs: AttributeSet?) : FloatingActionButton.Behavior() {

	// Changes visibility from GONE to INVISIBLE when fab is hidden because
	// due to CoordinatorLayout.onStartNestedScroll() implementation
	// child view's (here, fab) onStartNestedScroll won't be called anymore
	// because it's visibility is GONE
	private val listener = object : FloatingActionButton.OnVisibilityChangedListener() {
		override fun onHidden(fab: FloatingActionButton?) {
			fab?.visibility = View.INVISIBLE
		}
	}

	override fun onNestedPreScroll(
		layout: CoordinatorLayout,
		fab: FloatingActionButton,
		target: View,
		dx: Int,
		dy: Int,
		consumed: IntArray,
		type: Int
	) {
		super.onNestedPreScroll(layout, fab, target, dx, dy, consumed, type)
		if (dy > 0 && fab.visibility == View.VISIBLE) {
			fab.hide(listener)
		}
		else if (dy < 0 && fab.visibility == View.INVISIBLE) {
			fab.show()
		}
	}

	override fun onStartNestedScroll(
		layout: CoordinatorLayout,
		fab: FloatingActionButton,
		directTargetChild: View,
		target: View,
		axes: Int,
		type: Int
	): Boolean {
		// Ensure we react to vertical scrolling
		return axes == ViewCompat.SCROLL_AXIS_VERTICAL
	}
}