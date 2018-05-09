package com.newolf.compatinset

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import java.lang.reflect.Field


/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/4/10
 * 描述:
 * 历史:<br/>
 * ================================================
 */

class SingleInsetHolderView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val mStatusBarColor: Int

    private val insetHeight: Int
        get() = if (SHOW_INSET_HOLDER) getStatusBarHeight(getContext()) else 0

    init {

        val a = context.obtainStyledAttributes(attrs, R.styleable.InsetHolderView,
                defStyleAttr, R.style.Widget_CompatInset_InsetHolderView)
        mStatusBarColor = a.getColor(R.styleable.InsetHolderView_insetStatusBarColor, ContextCompat.getColor(context, android.R.color.transparent))

        translucentStatus()
    }

    private fun translucentStatus() {
        val window = (getContext() as Activity).window
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), insetHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (SHOW_INSET_HOLDER) canvas.drawColor(mStatusBarColor)
    }

    companion object {
        internal val SHOW_INSET_HOLDER: Boolean = Build.VERSION.SDK_INT >= 19

        fun getStatusBarHeight(context: Context): Int {
            val c: Class<*>
            val obj: Any
            val field: Field

            val x: Int
            var statusBarHeight = 0
            try {
                c = Class.forName("com.android.internal.R\$dimen")
                obj = c.newInstance()
                field = c.getField("status_bar_height")
                x = Integer.parseInt(field.get(obj).toString())
                statusBarHeight = context.getResources().getDimensionPixelSize(x)
            } catch (e1: Exception) {
                e1.printStackTrace()
            }

            return statusBarHeight
        }
    }
}
