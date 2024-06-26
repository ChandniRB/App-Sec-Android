package com.example.poc4.models

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import com.example.poc4.R
import java.util.regex.Pattern


class FormValidator(private val context: Context) {

    var isFormValid: Boolean  = true

    fun validateEmail(emailString: String) : ValidatorResponse {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        //        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        val emailValidatorResponse = ValidatorResponse(ValidatorResponse.STATUS_OK)
        var errorResponseMessage = ""

        if (TextUtils.isEmpty(emailString.trim())) {
            errorResponseMessage = context.getString(R.string.error_email_password_blank_email)
            emailValidatorResponse.status = ValidatorResponse.STATUS_ERROR
            isFormValid = false
        } else if(!pattern.matcher(emailString.trim()).matches()) {
            errorResponseMessage = context.getString(R.string.error_email_password_invalid_email_format)
            emailValidatorResponse.status = ValidatorResponse.STATUS_ERROR
            isFormValid = false
        }
        emailValidatorResponse.responseMessage = errorResponseMessage

        return emailValidatorResponse
    }

    fun validatePassword(passwordString: String, passwordLength: Int) : ValidatorResponse {
        val passwordValidatorResponse = ValidatorResponse(ValidatorResponse.STATUS_OK)
        var errorResponseMessage = ""

        if (TextUtils.isEmpty(passwordString.trim())) {
            errorResponseMessage = context.getString(R.string.error_email_password_blank_password)
            passwordValidatorResponse.status = ValidatorResponse.STATUS_ERROR
            isFormValid = false
        }else if (passwordString.trim().length < passwordLength) {
            errorResponseMessage = String.format(context.getString(R.string.error_email_password_invalid_password_length), passwordLength)
            passwordValidatorResponse.status = ValidatorResponse.STATUS_ERROR
            isFormValid = false
        }
        passwordValidatorResponse.responseMessage = errorResponseMessage

        return passwordValidatorResponse
    }

    fun validatePin(pinString: String, pinLength: Int): ValidatorResponse{
        val pinValidatorResponse = ValidatorResponse(ValidatorResponse.STATUS_OK)
        var errorResponseMessage = ""

        if(TextUtils.isEmpty(pinString.trim())){
            errorResponseMessage = context.getString(R.string.error_pin_blank_pin)
            pinValidatorResponse.status = ValidatorResponse.STATUS_ERROR
            isFormValid = false

        } else if(pinString.trim().length < pinLength){
            errorResponseMessage = String.format(context.getString(R.string.error_pin_invalid_pin_length), pinLength)
            pinValidatorResponse.status = ValidatorResponse.STATUS_ERROR
            isFormValid = false
        }
        pinValidatorResponse.responseMessage = errorResponseMessage
        return pinValidatorResponse
    }
}