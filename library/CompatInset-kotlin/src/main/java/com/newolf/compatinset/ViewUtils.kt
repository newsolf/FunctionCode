package com.newolf.compatinset

/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/4/10
 * 描述:
 * 历史:<br/>
 * ================================================
 */
object ViewUtils{
    fun objectEquals(a: Any?, b: Any?): Boolean {
        return a == b || a != null && a == b
    }
}