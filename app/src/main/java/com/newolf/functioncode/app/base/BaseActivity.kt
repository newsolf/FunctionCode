package com.newolf.functioncode.app.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View

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

    protected open fun creatContentView(@LayoutRes layoutId: Int): View {
        return LayoutInflater.from(this).inflate(layoutId, null)
    }

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
        getExtras(intent!!)
    }

    protected fun getExtras(intent: Intent) {
    }

    override fun loadData() {

    }

}

