package com.newolf.functioncode.app.sapn

import android.graphics.Color
import android.support.annotation.ColorInt
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance

/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * date :  2018/7/27
 * desc:
 * history:
 * ================================================
 */
class ForegroundAlphaColorSpan(@ColorInt var mColor: Int) : CharacterStyle()
, UpdateAppearance {


    fun setAlpha(alpha: Int) {
        mColor = Color.argb(alpha, Color.red(mColor), Color.green(mColor), Color.blue(mColor))
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.color = mColor
    }
}