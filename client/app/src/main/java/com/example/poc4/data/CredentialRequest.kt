package com.example.poc4.data

import kotlinx.serialization.Serializable

@Serializable
data class CredentialRequest(
    val challenge: String,
    val rpId: String,
    val allowCredentials: IntArray = intArrayOf(),
    val timeout: Long = 1800000,
    val userVerification: String =  "required"
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CredentialRequest

        return allowCredentials.contentEquals(other.allowCredentials)
    }

    override fun hashCode(): Int {
        return allowCredentials.contentHashCode()
    }
}
