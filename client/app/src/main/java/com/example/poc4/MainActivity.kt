package com.example.poc4

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.GetPublicKeyCredentialOption
import androidx.credentials.exceptions.GetCredentialException
import com.example.poc4.data.CredentialRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.security.SecureRandom
import java.util.Base64

class MainActivity: AppCompatActivity() {

    private val context = this@MainActivity

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val random = SecureRandom()
        val challenge = ByteArray(32)
        random.nextBytes(challenge)

        val challengeBase64: String =
            Base64.getUrlEncoder().withoutPadding().encodeToString(challenge)
        val rpId = context.packageName

        val requestJson = Json.encodeToString(CredentialRequest.serializer(), CredentialRequest(challengeBase64,rpId))
        val credentialManager = CredentialManager.create(context)
        val getPasswordOption = GetPasswordOption()
        val getPublicKeyCredentialOption = GetPublicKeyCredentialOption(
            requestJson = requestJson
        )

        val getCredRequest = GetCredentialRequest(
            listOf(getPasswordOption, getPublicKeyCredentialOption)
        )

        checkPinAndRedirect(credentialManager, getCredRequest)
    }
    private fun checkPinAndRedirect(
        credentialManager: CredentialManager,
        getCredRequest: GetCredentialRequest
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            try {

                var result = credentialManager.getCredential(
                    // Use an activity-based context to avoid undefined system UI
                    // launching behavior.
                    context = context,
                    request = getCredRequest
                )
                val intent = Intent(context, PassKeyActivity::class.java)
                startActivity(intent)
            } catch (e : GetCredentialException) {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
            }

    }
    }



