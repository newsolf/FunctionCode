package com.newolf.functioncode.activity

import android.os.CountDownTimer
import com.blankj.utilcode.util.BarUtils
import com.newolf.functioncode.R
import com.newolf.functioncode.app.Navigate
import com.newolf.functioncode.app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : BaseActivity() {
    val millisInFuture: Long = 6000
    val countDownInterval: Long = 1000
    lateinit var timer: CountDownTimer
    override fun bindLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        BarUtils.setStatusBarVisibility(this, false)
    }

    override fun loadData() {

         timer = object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onFinish() {
                nextToHome()
            }

            override fun onTick(millisUntilFinished: Long) {
                val l = millisUntilFinished / countDownInterval
                tvJump?.setText(getString(R.string.jump, l))
                if (l == 1.toLong()) {
                    nextToHome()
                }
            }

        }.start()


    }

    override fun initListener() {
        tvJump?.setOnClickListener({
            nextToHome()
        })
    }

    private fun nextToHome() {
        Navigate.startMainActivity(mContext)
        finish()
    }

    override fun finish() {
        super.finish()
        timer.cancel()
    }


}
