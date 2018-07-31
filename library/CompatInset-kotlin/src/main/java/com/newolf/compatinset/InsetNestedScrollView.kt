package com.newolf.compatinset

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet

/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * date :  2018/7/31
 * desc:
 * history:
 * ================================================
 */
class InsetNestedScrollView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    init {
        InsetHelper.setupForInsets(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        InsetHelper.requestApplyInsets(this)
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    override fun fitSystemWindows(insets: Rect): Boolean {
        return super.fitSystemWindows(InsetHelper.clearInset(insets))
    }
}