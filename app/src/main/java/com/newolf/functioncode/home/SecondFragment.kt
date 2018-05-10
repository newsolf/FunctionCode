package com.newolf.functioncode.home

import com.blankj.utilcode.util.BarUtils
import com.newolf.functioncode.R
import com.newolf.functioncode.app.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/3/30
 * 描述:
 * 历史:<br/>
 * ================================================
 */
class SecondFragment : BaseFragment() {
    override fun bindLayout(): Int {
        return R.layout.fragment_second
    }

    override fun initView() {



    }

    override fun initListener() {

    }


    override fun onStart() {
        super.onStart()
        BarUtils.setStatusBarLightMode(activity!!, true)
        refreshLayout.setBaseHeaderAdapter()
        refreshLayout.setOnHeaderRefreshListener { refreshLayout -> refreshLayout.postDelayed({ refreshLayout.onHeaderRefreshComplete() }, 500) }
        refreshLayout.setBaseFooterAdapter()
    }
}