package com.example.poc4.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.poc4.LoginActivity
import com.example.poc4.PassKeyActivity


class SignInUI private constructor(builder: Builder) {
    private val signInType: String?

    init {
        signInType = builder.signInType
        check(!(signInType == null || signInType.isEmpty())) { "The Sign In Type Parameter must be provided" }
        if (signInType == EMAIL_PASSWORD_FORM) {
            val emailIntent = Intent(builder.context, LoginActivity::class.java)
            emailIntent.putExtra(EXTRA_TITLE, builder.title)
            emailIntent.putExtra(EXTRA_SUBTITLE, builder.subtitle)
            emailIntent.putExtra(EXTRA_PASSWORD_LENGTH, builder.passwordLength)
            emailIntent.putExtra(EXTRA_IS_PIN_ENABLED, builder.isPinSignInEnabled)
            emailIntent.putExtra(EXTRA_IS_FINGEPRINT_ENABLED, builder.isFingerprintSignInEnabled)
            builder.context.startActivityForResult(emailIntent, REQUEST_CODE)
        } else if (signInType == PIN_FORM) {
            val pinIntent = Intent(builder.context, PassKeyActivity::class.java)
            pinIntent.putExtra(EXTRA_TITLE, builder.title)
            pinIntent.putExtra(EXTRA_SUBTITLE, builder.subtitle)
            pinIntent.putExtra(EXTRA_PIN_LENGTH, builder.pinLength)
            pinIntent.putExtra(EXTRA_IS_PIN_HIDDEN, builder.isPinHidden)
            pinIntent.putExtra(EXTRA_IS_EMAIL_ENABLED, builder.isEmailSignInEnabled)
            pinIntent.putExtra(EXTRA_IS_FINGEPRINT_ENABLED, builder.isFingerprintSignInEnabled)
            builder.context.startActivityForResult(pinIntent, REQUEST_CODE)
        }
    }

    class Builder(val context: AppCompatActivity) {
        var signInType: String? = null
         var title = ""
         var subtitle = ""
         var passwordLength = DEFAULT_PASSWORD_LENGTH
         var pinLength = DEFAULT_PIN_LENGTH
         var isEmailSignInEnabled = false
         var isPinSignInEnabled = false
         var isFingerprintSignInEnabled = false
         var isPinHidden = true
        fun setSignInType(signInType: String?): Builder {
            this.signInType = signInType
            return this
        }

        fun setPasswordLength(passwordLength: Int): Builder {
            this.passwordLength = passwordLength
            return this
        }

        fun setPinLength(pinLength: Int): Builder {
            this.pinLength = pinLength
            return this
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setSubtitle(subtitle: String): Builder {
            this.subtitle = subtitle
            return this
        }

        fun setEmailSignInEnabled(emailSignInEnabled: Boolean): Builder {
            isEmailSignInEnabled = emailSignInEnabled
            return this
        }

        fun setPinSignInEnabled(pinSignInEnabled: Boolean): Builder {
            isPinSignInEnabled = pinSignInEnabled
            return this
        }

        fun setFingerprintSignInEnabled(fingerprintSignInEnabled: Boolean): Builder {
            isFingerprintSignInEnabled = fingerprintSignInEnabled
            return this
        }

        fun setPinAsHidden(isPinHidden: Boolean): Builder {
            this.isPinHidden = isPinHidden
            return this
        }

        fun build(): SignInUI {
            return SignInUI(this)
        }
    }

    companion object {
        // constants
        const val EMAIL_PASSWORD_FORM = "EMAIL_PASSWORD_FORM"
        const val PIN_FORM = "PIN_FORM"
        const val FINGERPRINT_FORM = "FINGERPRINT_FORM"
        const val REQUEST_CODE = 100
        const val RESULT_OK = 69
        const val RESULT_CANCEL = 77
        const val DEFAULT_PASSWORD_LENGTH = 4
        const val DEFAULT_PIN_LENGTH = 4

        // variables used when passing data to various activities via intents
        const val EXTRA_PASSWORD_LENGTH = "EXTRA_PASSWORD_LENGTH"
        const val EXTRA_PIN_LENGTH = "EXTRA_PIN_LENGTH"
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_SUBTITLE = "EXTRA_SUBTITLE"
        const val EXTRA_IS_PIN_ENABLED = "EXTRA_IS_PIN_ENABLED"
        const val EXTRA_IS_EMAIL_ENABLED = "EXTRA_IS_EMAIL_ENABLED"
        const val EXTRA_IS_FINGEPRINT_ENABLED = "EXTRA_IS_FINGEPRINT_ENABLED"
        const val EXTRA_IS_PIN_HIDDEN = "EXTRA_IS_PIN_HIDDEN"
        const val PARAM_SIGN_IN_TYPE = "PARAM_SIGN_IN_TYPE"
        const val PARAM_RESULT_STATUS = "PARAM_RESULT_STATUS"
        const val PARAM_EMAIL = "PARAM_EMAIL"
        const val PARAM_PASSWORD = "PARAM_PASSWORD"
        const val PARAM_PIN = "PARAM_PIN"
    }
}