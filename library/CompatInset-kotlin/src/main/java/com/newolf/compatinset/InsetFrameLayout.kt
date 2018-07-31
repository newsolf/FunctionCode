package com.newolf.compatinset

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout


/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/4/10
 * 描述:
 * 历史:<br/>
 * ================================================
 */
class InsetFrameLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

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
