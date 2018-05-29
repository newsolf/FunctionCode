package com.newolf.functioncode.app.utils.helper

import android.annotation.TargetApi
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.widget.ImageView
import android.widget.TextView
import com.newolf.functioncode.R

@TargetApi(Build.VERSION_CODES.M)
/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * date :  2018/5/28
 * desc:
 * histry:
 * ================================================
 */
class FingerprintUiHelper(private val fingerprintManager: FingerprintManager, private val icon: ImageView, private val errorTextView: TextView, private val callback: Callback) : FingerprintManager.AuthenticationCallback() {
    private var cancellationSignal: CancellationSignal? = null
    private var selfCancelled = false
    fun startListening(cryptoObject: FingerprintManager.CryptoObject) {
        if (!isFingerprintAuthAvailable) return
        cancellationSignal = CancellationSignal()
        selfCancelled = false
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null)
        icon.setImageResource(R.mipmap.ic_fp_40px)
    }

    fun stopListening() {
        cancellationSignal?.also {
            selfCancelled = true
            it.cancel()
        }
        cancellationSignal = null
    }

    val isFingerprintAuthAvailable: Boolean
        get() = fingerprintManager.isHardwareDetected && fingerprintManager.hasEnrolledFingerprints()


    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        if (!selfCancelled) {
            showError(errString)
            icon.postDelayed({ callback.onError() }, ERROR_TIMEOUT_MILLIS)
        }
    }

    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
        errorTextView.run {
            removeCallbacks(resetErrorTextRunnable)
            setTextColor(errorTextView.resources.getColor(R.color.color_009688, null))
            text = "验证成功"
        }
        icon.run {
            setImageResource(R.drawable.ic_fingerprint_success)
            postDelayed({ callback.onAuthenticated() }, SUCCESS_DELAY_MILLIS)
        }
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
        showError(helpString)
    }

    override fun onAuthenticationFailed() {

    }

    interface Callback {
        fun onAuthenticated()

        fun onError()
    }

    private val resetErrorTextRunnable = Runnable {
        icon.setImageResource(R.mipmap.ic_fp_40px)
        errorTextView.run {
            setTextColor(errorTextView.resources.getColor(R.color.color_42000000, null))
            text = errorTextView.resources.getString(R.string.fingerprint_hint)
        }
    }

    private fun showError(error: CharSequence?) {
        icon.setImageResource(R.drawable.ic_fingerprint_error)
        errorTextView.run {
            text = error
            setTextColor(errorTextView.resources.getColor(R.color.color_F4511E, null))
            removeCallbacks(resetErrorTextRunnable)
            postDelayed(resetErrorTextRunnable, ERROR_TIMEOUT_MILLIS)
        }
    }

    companion object {
        val ERROR_TIMEOUT_MILLIS: Long = 1600
        val SUCCESS_DELAY_MILLIS: Long = 1300
    }
}