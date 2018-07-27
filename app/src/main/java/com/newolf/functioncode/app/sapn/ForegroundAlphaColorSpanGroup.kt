package com.newolf.functioncode.app.sapn

import java.util.ArrayList

/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * date :  2018/7/27
 * desc:
 * history:
 * ================================================
 */

class ForegroundAlphaColorSpanGroup(private val mAlpha: Float) {

    private val mSpans: ArrayList<ForegroundAlphaColorSpan>

    var alpha: Float
        get() = mAlpha
        set(alpha) {
            val size = mSpans.size
            var total = 1.0f * size.toFloat() * alpha
            for (index in 0 until size) {
                val span = mSpans[index]
                if (total >= 1.0f) {
                    span.setAlpha(255)
                    total -= 1.0f
                } else {
                    span.setAlpha((total * 255).toInt())
                    total = 0.0f
                }
            }
        }

    init {
        mSpans = ArrayList<ForegroundAlphaColorSpan>()
    }

    fun addSpan(span: ForegroundAlphaColorSpan) {
        span.setAlpha((mAlpha * 255).toInt())
        mSpans.add(span)
    }
}
