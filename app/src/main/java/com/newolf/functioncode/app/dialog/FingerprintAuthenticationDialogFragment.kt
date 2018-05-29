package com.newolf.functioncode.app.dialog

import android.annotation.TargetApi
import android.app.DialogFragment
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.newolf.functioncode.R
import com.newolf.functioncode.app.utils.helper.FingerprintUiHelper
import kotlinx.android.synthetic.main.fingerprint_dialog_content.*

/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * date :  2018/5/28
 * desc:
 * histry:
 * ================================================
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class FingerprintAuthenticationDialogFragment : DialogFragment(), FingerprintUiHelper.Callback {
    override fun onAuthenticated() {

    }

    override fun onError() {

    }

    private lateinit var cryptoObject: FingerprintManager.CryptoObject
    private lateinit var mFingerprintUiHelper: FingerprintUiHelper

    fun setCryptoObject(cryptoObject: FingerprintManager.CryptoObject) {
        this.cryptoObject = cryptoObject
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.setTitle(getString(R.string.sign_in))
        dialog.setCancelable(false)
        return inflater?.inflate(R.layout.fingerprint_dialog_container, container, false)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFingerprintUiHelper = FingerprintUiHelper(
                activity.getSystemService(FingerprintManager::class.java),
                fingerprint_icon,
                fingerprint_status,
                this
        )
    }

    override fun onStart() {
        super.onStart()
        mFingerprintUiHelper.startListening(cryptoObject)
    }

    override fun onPause() {
        super.onPause()
        mFingerprintUiHelper.stopListening()
    }

}