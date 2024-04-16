package com.example.poc4.data

import java.util.Objects

data class SearchResult(
    val id: String,
    val ver: String,
    val ts: String,
    val responseCode: String,
    val params: Params,
    val result: Objects,
)
