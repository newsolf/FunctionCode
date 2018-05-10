package com.newolf.functioncode.app.base


import android.support.annotation.LayoutRes
import android.support.design.widget.NavigationView
import com.newolf.functioncode.R
import com.newolf.functioncode.app.Navigate
import com.newolf.functioncode.app.configs.Constants
import kotlinx.android.synthetic.main.activity_drawer.*


/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/3/20
 * 描述:
 * 历史:<br/>
 * ================================================
 */
abstract class BaseDrawerActivity : BaseActivity() {

    val mListener = NavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.actionGitHub -> {
                Navigate.startInnerH5(mContext, Constants.GIT_HUB)
                return@OnNavigationItemSelectedListener true
            }
            R.id.actionBlog -> {
                Navigate.startInnerH5(mContext, Constants.BLOG)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun setBaseView(@LayoutRes layoutId: Int) {
        setContentView(R.layout.activity_drawer)
        val view = layoutInflater.inflate(layoutId, null)
        activityContainer.addView(view)
        navigationView.setNavigationItemSelectedListener(mListener)

    }




    override fun initListener() {

//        val toggle = ActionBarDrawerToggle(this,
//                rootLayout,
//                toolBar,
//                R.string.navigation_drawer_open,
//                R.string.navigation_drawer_close)
//        rootLayout.addDrawerListener(toggle)
//        toggle.syncState()
    }




}