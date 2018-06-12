package com.newolf.functioncode.app.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.BarUtils

/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/3/15
 * 描述:
 * 历史:<br/>
 * ================================================
 */
abstract class BaseActivity : AppCompatActivity(), IBaseView {
    lateinit var mContext: Context



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseView(bindLayout())
        mContext = this
        getExtras()
        initView()
        loadData()
        initListener()
    }

    protected open fun setBaseView(@LayoutRes layoutId: Int) {
        setContentView(layoutId)

    }

    override fun getExtras() {
        getExtras(intent)
    }

    protected fun getExtras(intent: Intent) {
    }

    override fun loadData() {

    }

    override fun onStart() {
        super.onStart()
        BarUtils.setNavBarImmersive(this)
    }


//    override fun onResume() {
//        super.onResume()
//        BarUtils.setNavBarImmersive(this)
//        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
//    }




}

