package com.newolf.functioncode.home

import com.blankj.utilcode.util.BarUtils
import com.newolf.functioncode.app.base.BaseFragment

/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/3/30
 * 描述:
 * 历史:<br/>
 * ================================================
 */
class OtherFragment : BaseFragment() {
    override fun onStart() {
        super.onStart()
        BarUtils.setStatusBarLightMode(activity, true)
    }
}