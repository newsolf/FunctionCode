package com.newolf.compatinset

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.support.v4.app.FragmentTabHost
import android.util.AttributeSet



/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/4/10
 * 描述:
 * 历史:<br/>
 * ================================================
 */
class InsetFragmentTabHost @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : FragmentTabHost(context, attrs) {

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