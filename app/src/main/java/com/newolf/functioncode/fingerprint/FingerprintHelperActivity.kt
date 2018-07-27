package com.newolf.functioncode.fingerprint

import android.app.Dialog
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.newolf.fingerprinthelper_kotlin.FingerprintHelper
import com.newolf.fingerprinthelper_kotlin.constants.ErrorType
import com.newolf.fingerprinthelper_kotlin.interfaces.HelperListener
import com.newolf.functioncode.R
import com.newolf.functioncode.app.base.BaseBackActivity
import com.newolf.functioncode.app.dialog.FullScreenDialog
import kotlinx.android.synthetic.main.activity_fingerprint_helper.*

class FingerprintHelperActivity : BaseBackActivity(), HelperListener {


    override fun getTitleResId(): Int {
        return R.string.fingerprint
    }

    private lateinit var verifyDialog: Dialog
    lateinit var mHelper: FingerprintHelper
    lateinit var tvShow:TextView

   private var isShow :Boolean = false


    /**
     * 绑定布局
     *
     * @return 布局 Id
     */
    override fun bindLayout(): Int {
        return R.layout.activity_fingerprint_helper
    }

    /**
     * 初始化View
     */
    override fun initView() {
        mHelper = FingerprintHelper.Builder(this, this)
                .setKeyName(javaClass.simpleName)
                .setTryTimeOut(60 * 1000L)
                .setLogEnable(true)
                .buid()
    }

    /**
     * 设置数据监听
     */
    override fun initListener() {
        btn2Verify.setOnClickListener({ showFingerprintVerifyDialog() })

    }

    private fun showFingerprintVerifyDialog() {

        verifyDialog = FullScreenDialog(mContext)

        verifyDialog.setCancelable(false)
        verifyDialog.setContentView(R.layout.dialog_verify)
        tvShow =verifyDialog.findViewById(R.id.tvShow)

        verifyDialog.setOnShowListener({ kotlin.run{isShow = true ;mHelper.startListening()}})
        verifyDialog.setOnDismissListener({isShow = false})
        verifyDialog.show()



    }

    override fun onFingerprintListening(listening: Boolean, milliseconds: Long) {

        LogUtils.e("listening =  $listening \t verifyDialog = $verifyDialog \t verifyDialog.isShow = {$verifyDialog.isShowing}")

        if (listening ){
            tvShow.setText("请验证已录入的指纹")
        }else if (milliseconds >1000){
            val left = milliseconds/1000
            tvShow.setText("还剩下 $left 秒 ")
        }
    }

    override fun onFingerprintStatus(authSuccessful: Boolean, errorType: Int, errorMess: CharSequence?) {

            tvShow.setText(errorMess)

        LogUtils.e("authSuccessful =  $authSuccessful \t verifyDialog = $verifyDialog \t verifyDialog.isShow = {$verifyDialog.isShowing}")

        if (authSuccessful) {
            ToastUtils.showShort("验证成功")
            verifyDialog.dismiss()
//            finish()
        }else {
            when (errorType) {
                ErrorType.General.HARDWARE_DISABLED, ErrorType.General.NO_FINGERPRINTS -> mHelper.showSecuritySettingsDialog()


            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isShow){
            mHelper.startListening()
        }
    }

    override fun onPause() {
        super.onPause()
//        verifyDialog.dismiss()
    }


}
