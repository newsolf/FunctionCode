package com.newolf.functioncode.activity

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Shader
import android.os.Bundle
import android.view.animation.LinearInterpolator
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SpanUtils
import com.newolf.functioncode.R
import com.newolf.functioncode.app.base.BaseBackActivity
import com.newolf.functioncode.app.sapn.ForegroundAlphaColorSpan
import com.newolf.functioncode.app.sapn.ForegroundAlphaColorSpanGroup
import com.newolf.functioncode.app.utils.QuittingTimeUtils
import kotlinx.android.synthetic.main.activity_quitting_time.*

class QuittingTimeActivity : BaseBackActivity() {
    lateinit var mShader: Shader
    var density = resources.displayMetrics.density
    internal lateinit var valueAnimator: ValueAnimator
    internal var mShaderWidth = 0f

    internal lateinit var matrix: Matrix

    internal lateinit var mForegroundAlphaColorSpanGroup: ForegroundAlphaColorSpanGroup


    override fun getTitleResId(): Int {
        return R.string.quittingtime
    }

    /**
     * 绑定布局
     *
     * @return 布局 Id
     */
    override fun bindLayout(): Int {
        return R.layout.activity_quitting_time
    }

    /**
     * 初始化View
     */
    override fun initView() {


    }

    override fun loadData() {
        QuittingTimeUtils.loadData()

        val setting = QuittingTimeUtils.setting

        initAnimSpan()

        startAnim()


    }

    private fun initAnimSpan() {
        mShaderWidth = 64 * density * 5f;
        LogUtils.e(mContext)
        LogUtils.e(mContext.resources)
        LogUtils.e(this.resources.getIntArray(R.array.rainbow))
        mShader = LinearGradient(0f, 0f, mShaderWidth, 0f,this.resources.getIntArray(R.array.rainbow), floatArrayOf(0f), Shader.TileMode.REPEAT)
        matrix = Matrix()

        val mPrinterString = "打印动画，后面的文字是为了测试打印效果..."


        val content = SpanUtils().appendLine("彩虹动画").setFontSize(64, true).setShader(mShader)

        var i = 0
        val len = mPrinterString.length
        while (i < len) {
            val span = ForegroundAlphaColorSpan(Color.TRANSPARENT)
            content.append(mPrinterString.substring(i, i + 1)).setSpans(span)
            mForegroundAlphaColorSpanGroup.addSpan(span)
            ++i
        }

        tvShow.setText(content.create().toString())
    }


    private fun startAnim() {
        valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            // shader
            matrix.reset()
            matrix.setTranslate(animation.animatedValue as Float * mShaderWidth, 0f)
            mShader.setLocalMatrix(matrix)

            // blur

        })

        valueAnimator.setInterpolator(LinearInterpolator())
        valueAnimator.setDuration((600 * 3).toLong())
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE)
        valueAnimator.start()
    }

    /**
     * 设置数据监听
     */
    override fun initListener() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quitting_time)
    }
}
