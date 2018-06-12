package com.newolf.functioncode.activity

import cn.bingoogolapple.qrcode.core.QRCodeView
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.newolf.functioncode.R
import com.newolf.functioncode.app.base.BaseBackActivity
import com.newolf.functioncode.app.utils.helper.DialogHelper
import kotlinx.android.synthetic.main.activity_zxing_scan.*

class ZxingScanActivity : BaseBackActivity(), QRCodeView.Delegate {
    override fun onScanQRCodeSuccess(result: String?) {

        ToastUtils.showShort("onScanQRCodeSuccess $result")
            enCode(result)
    }

     fun enCode(des: String?) {
        Thread(){kotlin.run {
         val btimap =    QRCodeEncoder.syncEncodeQRCode(des,200)
            runOnUiThread({
                if (!isFinishing){
                    ivShow.setImageBitmap(btimap)
                    zxingView.startSpotDelay(0)
                }
            })
        }}.start()

    }

    override fun onScanQRCodeOpenCameraError() {
        ToastUtils.showShort("打开相机失败")
    }

    override fun getTitleResId(): Int {
        return R.string.zxingScan
    }

    override fun bindLayout(): Int {
        return R.layout.activity_zxing_scan
    }

    override fun initView() {
        zxingView.setDelegate(this)
        request()

    }

    private fun request() = PermissionUtils.permission(PermissionConstants.CAMERA)
            .rationale { shouldRequest -> DialogHelper.showRationaleDialog(shouldRequest) }
//            .rationale { shouldRequest -> ToastUtils.showLong(shouldRequest.toString()) }
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: List<String>) {
                    LogUtils.d(permissionsGranted)
                    zxingView.startCamera()
                    zxingView.startSpotDelay(0)
                }

                override fun onDenied(permissionsDeniedForever: List<String>,
                                      permissionsDenied: List<String>) {
                    if (permissionsDeniedForever.isEmpty()) {
                        DialogHelper.showOpenAppSettingDialog()
//                        ToastUtils.showShort("没有获得权限")
                    }
                    LogUtils.d(permissionsDeniedForever, permissionsDenied)
                }
            })

            .request()

    override fun initListener() {

    }


    override fun onStop() {
        super.onStop()
        zxingView.stopSpot()
    }

    override fun onDestroy() {
        super.onDestroy()
        zxingView.onDestroy()
    }

}
