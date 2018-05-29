package com.newolf.functioncode.fingerprint

import android.app.KeyguardManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.support.annotation.RequiresApi
import com.blankj.utilcode.util.ToastUtils
import com.newolf.functioncode.R
import com.newolf.functioncode.app.base.BaseBackActivity
import com.newolf.functioncode.app.dialog.FingerprintAuthenticationDialogFragment
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

class FingerprintSettingActivity : BaseBackActivity() {
    private lateinit var keyStore: KeyStore
    private lateinit var keyGenerator: KeyGenerator
    private lateinit var cancellationSignal: CancellationSignal
    private lateinit var cryptoObject: FingerprintManager.CryptoObject
    private lateinit var defaultCipher: Cipher
    private lateinit var fingerprintManager: FingerprintManager

    val authenticationCallback: FingerprintManager.AuthenticationCallback = @RequiresApi(Build.VERSION_CODES.M)
    object : FingerprintManager.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
            super.onAuthenticationError(errorCode, errString)
            ToastUtils.showShort("onAuthenticationError " + errString + " errorCode = " + errorCode)
        }

        override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
            super.onAuthenticationSucceeded(result)
            ToastUtils.showShort("onAuthenticationSucceeded " + " result = " + result)
            ToastUtils.showShort("恭喜,验证成功")
        }

        override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
            super.onAuthenticationHelp(helpCode, helpString)
            ToastUtils.showShort("onAuthenticationHelp " + helpString + " helpCode = " + helpCode)
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            ToastUtils.showShort("onAuthenticationFailed ")
        }
    }

    override fun bindLayout(): Int {
        return R.layout.activity_fingerprint_setting
    }

    override fun initView() {
        supportActionBar?.setTitle(R.string.fingerprint)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setupKeyStoreAndKeyGenerator()
        }
    }


    override fun initListener() {

    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupKeyStoreAndKeyGenerator() {

        try {
            keyStore = KeyStore.getInstance(FUNCTION_KEY_STORE)
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, FUNCTION_KEY_STORE)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        val keyguardManager = getSystemService(KeyguardManager::class.java)
        fingerprintManager = getSystemService(FingerprintManager::class.java)

        if (!keyguardManager.isDeviceSecure) {
            ToastUtils.showShort("没有设置屏幕锁,请到设置中心进行设置")
            return
        }

        if (!fingerprintManager.hasEnrolledFingerprints()) {
            ToastUtils.showShort("没有设置指纹,请到设置中心进行指纹设置")
            return
        }

        createKey(DEFAULT_KEY_NAME, true)
        createKey(KEY_NAME_NOT_INVALIDATED, false)

        try {
            defaultCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7)
            cryptoObject = FingerprintManager.CryptoObject(defaultCipher)
            cancellationSignal = CancellationSignal()

        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get an instance of Cipher", e)
        } catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get an instance of Cipher", e)
        }

        if (initCipher(defaultCipher, DEFAULT_KEY_NAME)) {
            showFingerprintDialog()
        }

    }

    private fun showFingerprintDialog() {
        val fragment = FingerprintAuthenticationDialogFragment()
        fragment.setCryptoObject(cryptoObject)
        fragment.show(fragmentManager, DIALOG_FRAGMENT_TAG)
    }

    override fun onResume() {
        super.onResume()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            try {
//                if (initCipher(defaultCipher, DEFAULT_KEY_NAME)) {
//
//                    fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, authenticationCallback, null)
//                }
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
    }

    override fun onPause() {
        super.onPause()
//        if (!cancellationSignal.isCanceled) {
//            cancellationSignal.cancel()
//        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun createKey(keyName: String, invalidatedByBiometricEnrollment: Boolean) {
        try {
            keyStore.load(null)
            // Set the alias of the entry in Android KeyStore where the key will appear
            // and the constrains (purposes) in the constructor of the Builder

            val builder = KeyGenParameterSpec.Builder(keyName,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    // Require the user to authenticate with a fingerprint to authorize every use
                    // of the key
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)

            // This is a workaround to avoid crashes on devices whose API level is < 24
            // because KeyGenParameterSpec.Builder#setInvalidatedByBiometricEnrollment is only
            // visible on API level +24.
            // Ideally there should be a compat library for KeyGenParameterSpec.Builder but
            // which isn't available yet.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setInvalidatedByBiometricEnrollment(invalidatedByBiometricEnrollment)
            }
            keyGenerator.init(builder.build())
            keyGenerator.generateKey()


        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: InvalidAlgorithmParameterException) {
            throw RuntimeException(e)
        } catch (e: CertificateException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }


    }


    /**
     * Initialize the [Cipher] instance with the created key in the
     * [.createKey] method.
     *
     * @param keyName the key name to init the cipher
     * @return `true` if initialization is successful, `false` if the lock screen has
     * been disabled or reset after the key was generated, or if a fingerprint got enrolled after
     * the key was generated.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun initCipher(cipher: Cipher, keyName: String): Boolean {
        try {
            keyStore.load(null)
            val key = keyStore.getKey(keyName, null) as SecretKey
            cipher.init(Cipher.ENCRYPT_MODE, key)
            return true
        } catch (e: KeyPermanentlyInvalidatedException) {
            return false
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: CertificateException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: UnrecoverableKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: IOException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        }

    }

    companion object {
        private val FUNCTION_KEY_STORE = "AndroidKeyStore"
        private val DEFAULT_KEY_NAME = "default_key_name"
        private val KEY_NAME_NOT_INVALIDATED = "key_name_not_invalidated"
        private val DIALOG_FRAGMENT_TAG = "dialog_fragment_tag"
    }


}

