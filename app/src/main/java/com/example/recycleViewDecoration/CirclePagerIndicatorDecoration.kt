package com.example.recycleViewDecoration

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

/**
 * This class is using recycle view item decoration to show indicator in the upper right of the view
 */
class CirclePagerIndicatorDecoration(
    private val activeColorHex: Int,
    private val inactiveColorHex: Int,
    private val position: DecorationPosition
) : RecyclerView.ItemDecoration() {

    private val dp = Resources.getSystem().displayMetrics.density

    /**
     * Height of the space the indicator takes up at the top of the view.
     */
    private val mIndicatorHeight = (dp * 16).toInt()

    /**
     * Indicator stroke width.
     */
    private val mIndicatorStrokeWidth = dp * 10

    /**
     * Indicator width.
     */
    private val mIndicatorItemLength = dp * 18

    /**
     * Padding between indicators.
     */
    private val mIndicatorItemPadding = dp * 6

    /**
     * Some more natural animation interpolation
     */
    private val mInterpolator: Interpolator = AccelerateDecelerateInterpolator()

    private val mPaint = Paint()

    private var indicatorStartX: Float = 0.0F
    private var indicatorPosY: Double = 0.0

    /**
     * set the properties of the indicator.
     */
    init {
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = mIndicatorStrokeWidth
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true
    }

    private fun convertEnumToPosition(
        position: DecorationPosition,
        itemCount: Int,
        parentWidth: Int,
        parentHeight: Int
    ) {
        val totalLength = mIndicatorItemLength * itemCount
        val paddingBetweenItems = max(0, itemCount - 1) * mIndicatorItemPadding
        val indicatorTotalWidth = totalLength + paddingBetweenItems
        when (position) {
            DecorationPosition.BottomRight -> {
                indicatorStartX = parentWidth - .9f * indicatorTotalWidth
                indicatorPosY = parentHeight - .3 * mIndicatorHeight
            }
            DecorationPosition.BottomMiddle -> {
                indicatorStartX = (parentWidth / 2 - indicatorTotalWidth / 2.5).toFloat()
                indicatorPosY = parentHeight - .3 * mIndicatorHeight
            }
            DecorationPosition.BottomLeft -> {
                indicatorStartX = 0.0f
                indicatorPosY = parentHeight - .3 * mIndicatorHeight
            }
            DecorationPosition.UpperLeft -> {
                indicatorStartX = 0.0f
                indicatorPosY = .3 * mIndicatorHeight
            }
            DecorationPosition.UpperMiddle -> {
                indicatorStartX = (parentWidth / 2 - indicatorTotalWidth / 2.5).toFloat()
                indicatorPosY = .3 * mIndicatorHeight
            }
            DecorationPosition.UpperRight -> {
                indicatorStartX = parentWidth - .9f * indicatorTotalWidth
                indicatorPosY = .3 * mIndicatorHeight
            }
        }
    }

    /**
     *  this fun draws Indicator at the chosen location with the padding and margin needed
     */

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val itemCount = parent.adapter?.itemCount ?: 0
        // center horizontally, calculate width and subtract half from center

        convertEnumToPosition(position, itemCount, parent.width, parent.height)

        drawInactiveIndicators(c, indicatorStartX, indicatorPosY.toFloat(), itemCount)

        // find active page (which should be highlighted)
        val layoutManager = parent.layoutManager as LinearLayoutManager
        var activePosition = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (activePosition == RecyclerView.NO_POSITION) {
            return
        }

        // find offset of active page (if the user is scrolling)
        val activeChild = layoutManager.findViewByPosition(activePosition)
        if (activeChild != null) {
            val left = activeChild.left
            val width = activeChild.width
            // on swipe the active item will be positioned from [-width, 0]
            // interpolate offset for smooth animation
            val progress = mInterpolator.getInterpolation(left * -1 / width.toFloat())
            drawHighlights(c, indicatorStartX, indicatorPosY.toFloat(), activePosition, progress)
        } else {
            return
        }

    }

    /**
     *  this fun draws Inactive Indicators
     */
    private fun drawInactiveIndicators(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        itemCount: Int
    ) {
        mPaint.color = inactiveColorHex

        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding
        var start = indicatorStartX
        for (i in 0 until itemCount) {
            c.drawCircle(start + mIndicatorItemLength, indicatorPosY, itemWidth / 6, mPaint)
            start += itemWidth / 1.5F
        }
    }

    /**
     *  this fun draws active Indicators
     */
    private fun drawHighlights(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int, progress: Float
    ) {
        mPaint.color = activeColorHex

        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding
        val highlightStart = indicatorStartX + (itemWidth / 1.5F * highlightPosition)
        if (progress == 0f) {
            // no swipe, draw a normal indicator
            c.drawCircle(highlightStart, indicatorPosY, itemWidth / 6, mPaint)
        } else {
            // calculate partial highlight
            mIndicatorItemLength * progress
            c.drawCircle(
                highlightStart + mIndicatorItemLength,
                indicatorPosY,
                itemWidth / 6,
                mPaint
            )
        }
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = mIndicatorHeight
        outRect.top = mIndicatorHeight
    }

    enum class DecorationPosition {
        UpperRight,
        UpperMiddle,
        UpperLeft,
        BottomRight,
        BottomMiddle,
        BottomLeft
    }
}

