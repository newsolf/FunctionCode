package com.newolf.functioncode.app.base

/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/3/20
 * 描述:
 * 历史:<br/>
 * ================================================
 */
interface IBaseView  {
    /**
     * 绑定布局
     *
     * @return 布局 Id
     */
    fun bindLayout(): Int

    /***
     * 获取传递数据
     */
    fun getExtras()

    /**
     * 初始化View
     */
    fun initView()

    /**
     * 加载数据
     */
    fun loadData()

    /**
     * 设置数据监听
     */
    fun initListener()
}