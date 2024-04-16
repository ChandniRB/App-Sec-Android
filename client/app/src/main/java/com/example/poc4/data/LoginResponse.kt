package com.example.poc4.data

data class LoginResponse(
    val id: String,
    val ver: String,
    val ts: String,
    val responseCode: String,
    val params: Params,
    val result: Tokens,

)
