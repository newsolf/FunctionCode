package com.newolf.functioncode.app.base

import android.support.annotation.LayoutRes
import android.view.MenuItem
import com.blankj.utilcode.util.BarUtils
import com.newolf.functioncode.R
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_back.*

/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/3/20
 * 描述:
 * 历史:<br/>
 * ================================================
 */
abstract class BaseBackActivity : BaseActivity() {

    override fun setBaseView(@LayoutRes layoutId: Int)  {
        Slidr.attach(this)
        setContentView(R.layout.activity_back)
        activityContainer.addView(layoutInflater.inflate(layoutId,null))
        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(getTitleResId())

//        BarUtils.setStatusBarColor(this, ContextCompat.getColor(mContext, R.color.colorPrimary), 0)
//        BarUtils.addMarginTopEqualStatusBarHeight(rootLayout)
    }

    abstract fun getTitleResId(): Int

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        BarUtils.setStatusBarLightMode(this, true)
    }
}