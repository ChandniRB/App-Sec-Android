package com.example.poc4


import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.poc4.data.Credentials
import com.example.poc4.data.LoginResponse
import com.example.poc4.util.ApiService
import com.example.poc4.util.SSLPinningManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val loginButton = findViewById<Button>(R.id.login_btn)
        loginButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.username_et).text.toString()
            val password = findViewById<EditText>(R.id.password_et).text.toString()
            val credentials = Credentials(username, password)
            login(credentials)


        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun login(credentials: Credentials) {

        val context = this@LoginActivity
        val okHttpClient = SSLPinningManager.getOkHttpClient(this@LoginActivity);

        val retrofit = Retrofit.Builder()
            .baseUrl("https://10.0.2.2:8443") // Replace with your server base URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()


        val service: ApiService = retrofit.create(ApiService::class.java)

        service.login(context.packageName,credentials).enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                if (response.isSuccessful) {
                    val loginResponse: LoginResponse? = response.body()
                    if (loginResponse != null) {
                        val accessToken = loginResponse.result.accessToken
//                        val refreshToken = loginResponse.refreshToken

                        val masterKey: MasterKey = MasterKey.Builder(
                            this@LoginActivity,
                            MasterKey.DEFAULT_MASTER_KEY_ALIAS
                        )
                            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                            .build()
                        val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
                            this@LoginActivity,
                            "secret_shared_prefs",
                            masterKey,
                            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                        )
                        val editor = sharedPreferences.edit()
//                        editor.putString("apiKey", apiKeyResponse.apiKey)
                        editor.putString("accessToken", accessToken)
                        editor.putString("project", loginResponse.result.project)
                        editor.apply()

                        // Start Search Activity (if successful login)
                        val intent = Intent(this@LoginActivity, SearchActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Server error: " + response.code(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    "Network error: " + t.message,
                    Toast.LENGTH_LONG
                ).show()
                t.printStackTrace();
            }
        })











//        CoroutineScope(Dispatchers.Main).launch {
//            try {
////                val loginResponse = loginViewModel.login(credentials)
//                loginViewModel.login(credentials)
//                    .observe(this, Observer { loginResponse : LoginResponse ->
//                        // Handle login response
//                    })
//                val apiKeyResponse = apiKeyViewModel.key(loginResponse.accessToken)
//                val masterKey: MasterKey = MasterKey.Builder(
//                    this@LoginActivity,
//                    MasterKey.DEFAULT_MASTER_KEY_ALIAS
//                )
//                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
//                    .build()
//                val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
//                    this@LoginActivity,
//                    "secret_shared_prefs",
//                    masterKey,
//                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//                )
//                val editor = sharedPreferences.edit()
//                editor.putString("apiKey", apiKeyResponse.apiKey)
//                editor.putString("accessToken", loginResponse.accessToken)
//                editor.apply()
//                // Login successful, navigate to SearchActivity
//                navigateToSearchScreen()
//            } catch (e: Exception) {
//                println(e)
//            }
//        }
    }

//    private fun navigateToSearchScreen() {
//        val intent = Intent(this, SearchActivity::class.java)
//        startActivity(intent)
//        finish()
//    }
}
