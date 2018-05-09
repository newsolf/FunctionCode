package com.newolf.compatinset

import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v4.view.WindowInsetsCompat
import android.util.AttributeSet
import android.view.View
import java.lang.reflect.Field


/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/4/10
 * 描述:这是设置假状态栏
 * 历史:<br/>
 * ================================================
 */

class InsetHolderView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    internal var mLastInsets: WindowInsetsCompat? = null
    private val mStatusBarColor: Int

    private val insetHeight: Int
        get() = if (SHOW_INSET_HOLDER) {
            getStatusBarHeight(getContext())
        } else 0

    init {

        val a = context.obtainStyledAttributes(attrs, R.styleable.InsetHolderView,
                defStyleAttr, R.style.Widget_CompatInset_InsetHolderView)
        mStatusBarColor = a.getColor(R.styleable.InsetHolderView_insetStatusBarColor, ContextCompat.getColor(context, android.R.color.transparent))

        ViewCompat.setOnApplyWindowInsetsListener(this,
                object : android.support.v4.view.OnApplyWindowInsetsListener {
                  override  fun onApplyWindowInsets(v: View,
                                            insets: WindowInsetsCompat): WindowInsetsCompat {
                        return onWindowInsetChanged(insets)
                    }
                })
    }

    internal fun onWindowInsetChanged(insets: WindowInsetsCompat): WindowInsetsCompat {
        var newInsets: WindowInsetsCompat? = null
        if (ViewCompat.getFitsSystemWindows(this)) {
            newInsets = insets
        }
        if (!ViewUtils.objectEquals(mLastInsets, newInsets)) {
            mLastInsets = newInsets
            requestLayout()
        }
        return insets.consumeSystemWindowInsets()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        InsetHelper.requestApplyInsets(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), insetHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(mStatusBarColor)
    }

    companion object {
        internal val SHOW_INSET_HOLDER = Build.VERSION.SDK_INT >= 19

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
