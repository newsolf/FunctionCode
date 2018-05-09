package com.newolf.compatinset

import android.graphics.Rect
import android.os.Build
import android.support.v4.view.ViewCompat
import android.support.v4.view.WindowInsetsCompat
import android.view.View


/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/4/10
 * 描述:
 * 历史:<br/>
 * ================================================
 */
object InsetHelper {
    fun setupForInsets(view: View) {
        if (Build.VERSION.SDK_INT < 21) {
            return
        }
        if (ViewCompat.getFitsSystemWindows(view)) {

            ViewCompat.setOnApplyWindowInsetsListener(view, object : android.support.v4.view.OnApplyWindowInsetsListener {
                override fun onApplyWindowInsets(v: View, insets: WindowInsetsCompat): WindowInsetsCompat {
                    view.requestLayout()
                    return insets
                }
            })
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        } else {
            ViewCompat.setOnApplyWindowInsetsListener(view, null)
        }
    }

    fun requestApplyInsets(view: View) {
        ViewCompat.requestApplyInsets(view)
    }

    fun clearInset(insets: Rect): Rect {
        insets.top = 0
        insets.right = 0
        insets.right = 0
        return insets
    }
}