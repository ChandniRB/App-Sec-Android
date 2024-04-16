package com.example.poc4.util

import com.example.poc4.data.ApiKeyResponse
import com.example.poc4.data.Credentials
import com.example.poc4.data.LoginResponse
import com.example.poc4.data.SearchResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/apis/auth/login")
    fun login(@Header("client") client: String, @Body credentials: Credentials): Call<LoginResponse>

    @GET("/apis/user/read/{userId}")
    fun search(@Header("Authorization") accessToken: String, @Header("client-token") integrityToken: String, @Header("client") client: String, @Path("userId") userId: String, @Header("api-key") apiKey: String): Call<SearchResult>

    @GET("key")
    fun key(@Header("Authorization") accessToken: String): Call<ApiKeyResponse>


}