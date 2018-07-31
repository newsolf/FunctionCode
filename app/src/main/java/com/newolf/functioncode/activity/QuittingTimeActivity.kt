package com.newolf.functioncode.activity

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Shader
import android.text.SpannableStringBuilder
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.TextView
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.TimeUtils
import com.newolf.functioncode.R
import com.newolf.functioncode.app.base.BaseBackActivity
import com.newolf.functioncode.app.sapn.ForegroundAlphaColorSpan
import com.newolf.functioncode.app.sapn.ForegroundAlphaColorSpanGroup
import com.newolf.functioncode.app.utils.QuittingTimeUtils
import com.newolf.functioncode.data.QuittingTimeSetting
import kotlinx.android.synthetic.main.activity_quitting_time.*


class QuittingTimeActivity : BaseBackActivity() {
    lateinit var mShader: Shader
    var density = 0f
    internal lateinit var valueAnimator: ValueAnimator
    internal var mShaderWidth = 0f

    internal lateinit var matrix: Matrix
    lateinit var ssb: SpannableStringBuilder

    internal lateinit var mForegroundAlphaColorSpanGroup: ForegroundAlphaColorSpanGroup
    lateinit var tvShow: TextView
    lateinit var setting: QuittingTimeSetting




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

        tvShow = findViewById(R.id.tvShow)
    }

    override fun loadData() {
        QuittingTimeUtils.loadData()

        setting = QuittingTimeUtils.setting

        llNO.visibility = if (setting.isSet) View.GONE else View.VISIBLE
        llYes.visibility =  if (!setting.isSet) View.GONE else View.VISIBLE

        if (setting.isSet){
            initAnimSpan(System.currentTimeMillis())
            startAnim()
        }





    }

    private fun initAnimSpan(mill: Long) {
        density = resources.displayMetrics.density
        mForegroundAlphaColorSpanGroup = ForegroundAlphaColorSpanGroup(0f)
        mShaderWidth = 64 * density * 2f;
        mShader = LinearGradient(0f, 0f, mShaderWidth, 0f, resources.getIntArray(R.array.rainbow), null, Shader.TileMode.REPEAT)
        matrix = Matrix()

        val mPrinterString = "继续坚持,别放弃!\n改变从习惯开始..."

        val content = SpanUtils()

        val text1 = "从 " + TimeUtils.millis2String(mill)
        val text2 = "到现在已成功戒烟 "
        val text3 = TimeUtils.getFitTimeSpanByNow(mill, 4)

        content.appendLine(text1).setFontSize(30, true).setShader(mShader)
        content.appendLine("").setFontSize(10, true).setShader(mShader)
        content.appendLine(text2).setFontSize(36, true).setShader(mShader)
        content.appendLine("").setFontSize(10, true).setShader(mShader)
        content.appendLine(text3).setFontSize(38, true).setShader(mShader)
        content.appendLine("").setFontSize(160, true).setShader(mShader)
        content.appendLine("").setFontSize(160, true).setShader(mShader)
        var i = 0
        val len = mPrinterString.length
        while (i < len) {
            val span = ForegroundAlphaColorSpan(Color.TRANSPARENT)
            content.append(mPrinterString.substring(i, i + 1)).setSpans(span).setFontSize(48, true).setShader(mShader)
            mForegroundAlphaColorSpanGroup.addSpan(span)
            ++i
        }

        ssb = content.create()

    }




    private fun startAnim() {
        valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.addUpdateListener({ animation ->
            // shader
            matrix.reset()
            matrix.setTranslate(animation.animatedValue as Float * mShaderWidth, 0f)
            mShader.setLocalMatrix(matrix)

            // blur
            mForegroundAlphaColorSpanGroup.setAlpha(animation.animatedValue as Float)
            tvShow.setText(ssb)


        })
        valueAnimator.addListener(object: Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                initAnimSpan(setting.startTime)
            }

            override fun onAnimationEnd(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })

        valueAnimator.setInterpolator(LinearInterpolator())
        valueAnimator.setDuration((1000).toLong())
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE)
        valueAnimator.start()
    }

    /**
     * 设置数据监听
     */
    override fun initListener() {
        btnOK.setOnClickListener({ kotlin.run {
            QuittingTimeUtils.saveStartTime()
            loadData()
        } })

        tvReset.setOnClickListener({kotlin.run {
            QuittingTimeUtils.reset()
            loadData()
        }})
    }

    override fun onDestroy() {
        super.onDestroy()
        valueAnimator.cancel()
    }


}
