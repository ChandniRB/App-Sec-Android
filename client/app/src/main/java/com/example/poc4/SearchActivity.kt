package com.example.poc4

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.poc4.data.SearchResult
import com.example.poc4.util.ApiService
import com.example.poc4.util.SSLPinningManager
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.play.core.integrity.IntegrityManagerFactory
import com.google.android.play.core.integrity.StandardIntegrityManager
import com.google.android.play.core.integrity.StandardIntegrityManager.StandardIntegrityToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest

class SearchActivity : AppCompatActivity() {

    var integrityTokenProvider: StandardIntegrityManager.StandardIntegrityTokenProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)
        getTokenProvider()
        val searchButton = findViewById<Button>(R.id.search_btn)
        searchButton.setOnClickListener {
            search()
        }
    }

    private fun getTokenProvider(){
        val integrityManager: StandardIntegrityManager = IntegrityManagerFactory.createStandard(applicationContext)
        integrityManager.prepareIntegrityToken(
            StandardIntegrityManager.PrepareIntegrityTokenRequest.builder()
                .setCloudProjectNumber(fetchSharedPreference("project").toLong())
                .build())
            .addOnSuccessListener{
                    tokenProvider ->  integrityTokenProvider = tokenProvider
            }
            .addOnFailureListener {
                e: Exception -> println(e)
            }
    }

    private fun findHash(userId: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val bytes = md.digest(userId.toByteArray())
//        val digest = md.digest(bytes)
        val sb = StringBuilder()
        for (b in bytes) {
            sb.append(String.format("%02X", b))
        }
return  sb.toString()
//        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

    private fun fetchSharedPreference(key: String): String {
        val context: Context = this@SearchActivity
        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return sharedPreferences.getString(
            key,
            ""
        ).toString()

    }

    private fun search() {

        val context: Context = this@SearchActivity
        val okHttpClient = SSLPinningManager.getOkHttpClient(context);

        val retrofit = Retrofit.Builder()
            .baseUrl("https://10.0.2.2:8443") // Replace with your server base URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val userId = findViewById<EditText>(R.id.search_field).text.toString()
        val service: ApiService = retrofit.create(ApiService::class.java)

                val apiKey = fetchSharedPreference("apiKey")
                val accessToken = "Bearer " + fetchSharedPreference("accessToken")
        val hashedUserId = findHash(userId)

        val integrityTokenResponse: Task<StandardIntegrityManager.StandardIntegrityToken>? =
            integrityTokenProvider?.request(StandardIntegrityManager
                .StandardIntegrityTokenRequest
                .builder()
                .setRequestHash(hashedUserId)
                .build())

        integrityTokenResponse
            ?.addOnSuccessListener(OnSuccessListener<StandardIntegrityToken> { response: StandardIntegrityToken ->
                service.search(accessToken,response.token(), context.packageName,userId,apiKey ).enqueue(object : Callback<SearchResult?> {
                    override fun onResponse(call: Call<SearchResult?>, response: Response<SearchResult?>) {
                        if (response.isSuccessful) {
                            val searchResult: SearchResult? = response.body()
                            if (searchResult != null) {
                                val result = searchResult.result.toString()
                                val resultView = findViewById<TextView>(R.id.search_results_tv)
                                resultView.text = result

                            } else {
                                Toast.makeText(context, "Login failed!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Server error: " + response.code(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<SearchResult?>, t: Throwable) {
                        Toast.makeText(
                            context,
                            "Network error: " + t.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            })
            ?.addOnFailureListener {
                    exception: Exception? -> println(exception)
            }

    }
}