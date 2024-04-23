package com.example.poc4

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CreatePublicKeyCredentialRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetPasswordOption
import androidx.credentials.GetPublicKeyCredentialOption
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialCustomException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.CreateCredentialInterruptedException
import androidx.credentials.exceptions.CreateCredentialProviderConfigurationException
import androidx.credentials.exceptions.CreateCredentialUnknownException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.publickeycredential.CreatePublicKeyCredentialDomException
import com.example.poc4.data.CredentialRequest
import com.example.poc4.models.FormValidator
import com.example.poc4.models.SignInUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.security.SecureRandom
import java.util.Base64


class PassKeyActivity : AppCompatActivity() {
    private val context = this@PassKeyActivity
    private var pinLength = SignInUI.DEFAULT_PIN_LENGTH
    private var currentPinInput = ""
    private var initpin = ""
    private lateinit var credentialManager: CredentialManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pin_activity)

        val pinCheckBtn = findViewById<ImageView>(R.id.pin_check_btn)
        val pinVerificationCode = findViewById<EditText>(R.id.pin_verification_code)
        val random = SecureRandom()
        val challenge = ByteArray(32)
        random.nextBytes(challenge)

        val challengeBase64: String =
            Base64.getUrlEncoder().withoutPadding().encodeToString(challenge)
        val rpId = context.packageName

        val requestJson = Json.encodeToString(CredentialRequest.serializer(), CredentialRequest(challengeBase64,rpId))
        val getPasswordOption = GetPasswordOption()
        val getPublicKeyCredentialOption = GetPublicKeyCredentialOption(
            requestJson = requestJson
        )

        val getCredRequest = GetCredentialRequest(
            listOf(getPasswordOption, getPublicKeyCredentialOption)
        )

        pinCheckBtn.setOnClickListener{
            if (validateFields()) {
                val pinIntent = Intent()
                pinIntent.putExtra(SignInUI.PARAM_SIGN_IN_TYPE, SignInUI.PIN_FORM)
                pinIntent.putExtra(SignInUI.PARAM_PIN, pinVerificationCode.text.toString())
                setResult(SignInUI.RESULT_OK, pinIntent)
                if(pinExists( getCredRequest)){
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            val result = credentialManager.getCredential(
                                // Use an activity-based context to avoid undefined system UI
                                // launching behavior.
                                context = context,
                                request = getCredRequest
                            )
                            handleSignIn(result)
                        } catch (e : GetCredentialException) {
                            println(e)
                        }
                    }

                }
                else
                    createPasskey(false)
                finish()
            }
        }

        setupUI()




    }

    private fun pinExists(
        getCredRequest: GetCredentialRequest
    ): Boolean {
        var pinExists = false
        try {
        CoroutineScope(Dispatchers.Main).launch {

                var result = credentialManager.getCredential(
                    // Use an activity-based context to avoid undefined system UI
                    // launching behavior.
                    context = context,
                    request = getCredRequest
                )
                pinExists = true

        }
        } catch (e : GetCredentialException) {
            pinExists = false
        }
        return pinExists
    }

    private fun setupUI() {
        val isEmailEnabled = intent.getBooleanExtra(SignInUI.EXTRA_IS_EMAIL_ENABLED, false)
        val isFingerprintEnabled = intent.getBooleanExtra(SignInUI.EXTRA_IS_FINGEPRINT_ENABLED, false)
        pinLength = intent.getIntExtra(SignInUI.EXTRA_PIN_LENGTH, SignInUI.DEFAULT_PIN_LENGTH)
        val isPinHidden = intent.getBooleanExtra(SignInUI.EXTRA_IS_PIN_HIDDEN, true)
        val pinVerificationCode = findViewById<EditText>(R.id.pin_verification_code)


        if (!isPinHidden) {
            pinVerificationCode.inputType = InputType.TYPE_CLASS_NUMBER
        }
    }

    private fun validateFields() : Boolean {
        val formValidator = FormValidator(context)
        val pinVerificationCode = findViewById<EditText>(R.id.pin_verification_code)

        val pinValidator = formValidator.validatePin(pinVerificationCode.text.toString(), pinLength)
        if (!pinValidator.isFormValid()) {
            pinVerificationCode.error = pinValidator.responseMessage
            Toast.makeText(context, pinVerificationCode.error, Toast.LENGTH_SHORT).show()

        }

        return formValidator.isFormValid
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        when (val credential = result.credential) {
            is PublicKeyCredential -> {
                val responseJson = credential.authenticationResponseJson
                // Share responseJson i.e. a GetCredentialResponse on your server to
                // validate and  authenticate
            }
            is PasswordCredential -> {
                val username = credential.id
                val password = credential.password
                // Use id and password to send to your server to validate
                // and authenticate
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createPasskey(preferImmediatelyAvailableCredentials: Boolean) {
        val random = SecureRandom()
        val challenge = ByteArray(32)
        random.nextBytes(challenge)

        val challengeBase64: String =
            Base64.getUrlEncoder().withoutPadding().encodeToString(challenge)
        val rpId = context.packageName

        val requestJson = """{
  "challenge": "$challengeBase64",
  "rp": {
    "name": "Credential Manager example",
    "id": "$rpId"
  },
  "user": {
    "id": "testadmin",
    "name": "Test Admin",
    "displayName": "Test Admin"
  },
  "pubKeyCredParams": [
    {
      "type": "public-key",
      "alg": -7
    },
    {
      "type": "public-key",
      "alg": -257
    }
  ],
  "timeout": 1800000,
  "attestation": "none",
  "excludeCredentials": [
    {"id": "ghi789", "type": "public-key"},
    {"id": "jkl012", "type": "public-key"}
  ],
  "authenticatorSelection": {
    "authenticatorAttachment": "platform",
    "requireResidentKey": true,
    "residentKey": "required",
    "userVerification": "required"
  }
}
"""

        val createPublicKeyCredentialRequest = CreatePublicKeyCredentialRequest(
            // Contains the request in JSON format. Uses the standard WebAuthn
            // web JSON spec.
            requestJson = requestJson,
            // Defines whether you prefer to use only immediately available
            // credentials, not hybrid credentials, to fulfill this request.
            // This value is false by default.
            preferImmediatelyAvailableCredentials = preferImmediatelyAvailableCredentials,
        )

        // Execute CreateCredentialRequest asynchronously to register credentials
        // for a user account. Handle success and failure cases with the result and
        // exceptions, respectively.
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = credentialManager.createCredential(
                    // Use an activity-based context to avoid undefined system
                    // UI launching behavior
                    context = context,
                    request = createPublicKeyCredentialRequest,
                )
                val intent = Intent(context, SearchActivity::class.java)
                startActivity(intent)

            } catch (e : CreateCredentialException){
                handleFailure(e)
            }
        }
    }

    fun handleFailure(e: CreateCredentialException) {
        when (e) {
            is CreatePublicKeyCredentialDomException -> {
                // Handle the passkey DOM errors thrown according to the
                // WebAuthn spec.
                println(e.domError)
            }
            is CreateCredentialCancellationException -> {
                // The user intentionally canceled the operation and chose not
                // to register the credential.
            }
            is CreateCredentialInterruptedException -> {
                // Retry-able error. Consider retrying the call.
            }
            is CreateCredentialProviderConfigurationException -> {
                // Your app is missing the provider configuration dependency.
                // Most likely, you're missing the
                // "credentials-play-services-auth" module.
            }
            is CreateCredentialUnknownException -> {

            }
            is CreateCredentialCustomException -> {
                // You have encountered an error from a 3rd-party SDK. If you
                // make the API call with a request object that's a subclass of
                // CreateCustomCredentialRequest using a 3rd-party SDK, then you
                // should check for any custom exception type constants within
                // that SDK to match with e.type. Otherwise, drop or log the
                // exception.
            }
            else -> Log.w(TAG, "Unexpected exception type ${e::class.java.name}")
        }
    }

    fun onClick(view: View) {
        val pinVerificationCode = findViewById<EditText>(R.id.pin_verification_code)
        if(view.id == R.id.pin_one ) {
            currentPinInput += "1"
            pinVerificationCode.setText(currentPinInput)

        }
        else if(view.id == R.id.pin_two){
            currentPinInput += "2"
            pinVerificationCode.setText(currentPinInput)
        }
        else if(view.id == R.id.pin_three){
            currentPinInput += "3"
            pinVerificationCode.setText(currentPinInput)

        }
        else if(view.id == R.id.pin_four){
            currentPinInput += "4"
            pinVerificationCode.setText(currentPinInput)

        }
        else if(view.id == R.id.pin_five){
            currentPinInput += "5"
            pinVerificationCode.setText(currentPinInput)

        }
        else if(view.id == R.id.pin_six){
            currentPinInput += "6"
            pinVerificationCode.setText(currentPinInput)

        }
        else if(view.id == R.id.pin_seven){
            currentPinInput += "7"
            pinVerificationCode.setText(currentPinInput)

        }
        else if(view.id == R.id.pin_eight){
            currentPinInput += "8"
            pinVerificationCode.setText(currentPinInput)

        }
        else if(view.id == R.id.pin_nine) {
            currentPinInput += "9"
            pinVerificationCode.setText(currentPinInput)

        }
        else if(view.id == R.id.pin_zero){
            currentPinInput += "0"
            pinVerificationCode.setText(currentPinInput)

        }
        else if (view.id == R.id.pin_cancel_btn){

            currentPinInput = ""
            pinVerificationCode.setText(initpin)

        }
        else if(view.id == R.id.pin_email_login){
            val homeIntent = Intent(this,LoginActivity::class.java)
            startActivity(homeIntent)
        }
    }


}
