package com.example.poc4.util

import android.content.Context
import okhttp3.OkHttpClient
import java.io.IOException
import java.io.InputStream
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


object SSLPinningManager {
    @Throws(GeneralSecurityException::class, IOException::class)
    fun getOkHttpClient(context: Context): OkHttpClient {
        // Load SSL certificate from assets folder
        var inputStream: InputStream? = null
        return try {
            val assetManager = context.assets
            inputStream = assetManager.open("localhost.crt")
            val certificate = readCertificate(inputStream)

            // Create SSL socket factory with the loaded certificate
            val trustManager = getTrustManager(certificate)
            val builder = OkHttpClient.Builder()
                .sslSocketFactory(getSSLSocketFactory(trustManager), trustManager)
                .hostnameVerifier(({ hostname, session -> true }))
            builder.build()
        } finally {
            inputStream?.close()
        }
    }

    @Throws(GeneralSecurityException::class, IOException::class)
    private fun readCertificate(inputStream: InputStream): X509Certificate {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        return certificateFactory.generateCertificate(inputStream) as X509Certificate
    }

    @Throws(GeneralSecurityException::class)
    private fun getTrustManager(certificate: X509Certificate): X509TrustManager {
        val trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        val ks: KeyStore? = null
        trustManagerFactory.init(ks)
        val defaultTrustManager = trustManagerFactory.trustManagers[0] as X509TrustManager
        return object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

            @Throws(GeneralSecurityException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                // Check if the server's certificate matches the pinned certificate
                for (cert in chain) {
                    cert.checkValidity()
                    if (certificate == cert) {
                        return  // Certificate is trusted
                    }
                }
                throw CertificateException("Server certificate does not match the pinned certificate")
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return defaultTrustManager.acceptedIssuers
            }
        }
    }

    @Throws(GeneralSecurityException::class)
    private fun getSSLSocketFactory(trustManager: X509TrustManager): SSLSocketFactory {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
        return sslContext.socketFactory
    }
}

