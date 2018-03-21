package com.newolf.functioncode.app

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.blankj.utilcode.util.LogUtils


/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/3/15
 * 描述:
 * 历史:<br/>
 * ================================================
 */
open class Navigate {
    companion object {
        private fun startActivity(context: Context, clz: Class<*>) {
            context.startActivity(Intent(context, clz))
        }

        fun startInnerH5(content: Context, url: String) {
            LogUtils.e(url)
            content.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }
}