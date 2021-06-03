package org.grupotres.appcongreso.ui.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import kotlin.math.abs

/**
 * A View to draw using touch events
 */
class TouchDrawingView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyle: Int = 0) : View(context, attrs, defStyle) {

	private val paint = Paint().apply {
		isAntiAlias = true
		color = Color.BLUE
		isDither = true
		style = Paint.Style.STROKE
		strokeJoin = Paint.Join.ROUND
		strokeCap = Paint.Cap.ROUND
		strokeWidth = 10.0f
	}
	private val path = Path()

	// Cache X and Y coordinates for the current touch event
	private var motionTouchEventX = 0f
	private var motionTouchEventY = 0f

	// Cache the lastest X and Y values
	private var currentX = 0f
	private var currentY = 0f

	// To detect if the user is drawing or wants to scroll
	private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

	override fun onDraw(canvas: Canvas?) {
		canvas?.drawPath(path, paint)
		super.onDraw(canvas)
	}

	@SuppressLint("ClickableViewAccessibility")
	override fun onTouchEvent(event: MotionEvent): Boolean {
		motionTouchEventX = event.x
		motionTouchEventY = event.y

		when (event.action) {
			MotionEvent.ACTION_DOWN -> touchStart()
			MotionEvent.ACTION_MOVE -> touchMove()
		}
		return true
	}

	private fun touchStart() {
		path.moveTo(motionTouchEventX, motionTouchEventY)
		currentX = motionTouchEventX
		currentY = motionTouchEventY
	}

	private fun touchMove() {
		val dx = abs(motionTouchEventX - currentX)
		val dy = abs(motionTouchEventY - currentY)

		if (dx >= touchTolerance || dy >= touchTolerance) {
			// QuadTo() adds a quadratic bezier from the last point,
			// approaching control point (x1,y1), and ending at (x2,y2).
			path.quadTo(currentX, currentY, (motionTouchEventX + currentX) / 2,
						(motionTouchEventY + currentY) / 2)
			currentX = motionTouchEventX
			currentY = motionTouchEventY
		}
		invalidate()
	}

	fun clear() {
		path.reset()
		invalidate()
	}
}