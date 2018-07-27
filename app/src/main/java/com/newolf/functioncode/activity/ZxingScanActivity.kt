package com.newolf.functioncode.activity

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
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
        Thread() {
            kotlin.run {
                val btimap = QRCodeEncoder.syncEncodeQRCode(des, 400)
                runOnUiThread({
                    if (!isFinishing) {
                        ivShow.setImageBitmap(btimap)
                        zxingView.startSpotDelay(0)
                    }
                })
            }
        }.start()

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
        btnOpen.setOnClickListener({
            openSystem()
        })
    }

    private val REQUEST_CODE_CHOOSE = 0x11

    private fun openSystem() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intent, REQUEST_CODE_CHOOSE)
    }


    override fun onStop() {
        super.onStop()
        zxingView.stopSpot()
    }

    override fun onDestroy() {
        super.onDestroy()
        zxingView.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CHOOSE) {
                val uri = data?.data
                LogUtils.e("uri = $uri")
                if (!TextUtils.isEmpty(uri?.toString())) {
                    createBitmap(uri)
                }else{
                    LogUtils.e("uri = null")
                }
            }
        }
    }

    private fun createBitmap(uri: Uri?) {



        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        if (cursor != null) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            cursor.moveToFirst()
            val filePath =  cursor.getString(column_index)
            val  bitmap = BitmapFactory.decodeFile(filePath)
            ivShow.setImageBitmap(bitmap)
        }
    }
}
