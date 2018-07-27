package com.newolf.functioncode.home

import com.newolf.functioncode.R
import com.newolf.functioncode.app.Navigate
import com.newolf.functioncode.app.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_other.*

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
    override fun bindLayout(): Int {
        return R.layout.fragment_other
    }


    override fun initListener() {
        btnFingerprint.setOnClickListener({ Navigate.startFingerprintSettingActivity(mContext) })
        btnScan.setOnClickListener({ Navigate.startZxingScanActivity(mContext) })
        btnFingerprintHelper.setOnClickListener({ Navigate.startFingerprintHelperActivity(mContext) })
        btnQuittingTime.setOnClickListener({ Navigate.startQuittingTimeActivity(mContext) })
    }



}


