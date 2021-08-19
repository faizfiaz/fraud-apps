package com.fitness.tracking.utils

import com.fitness.tracking.domain.exceptions.CertPathException
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

class SSLProvider {
    private var trustManager: TrustManager? = null
    private val sslContext: SSLContext? = SSLContext.getInstance("TLS")

    @Throws(CertPathException::class)
    fun assemble() = try {
        val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
        )
        trustManager = trustAllCerts[0]
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null, null)
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)
        val keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        keyManagerFactory.init(keyStore, "keystore_pass".toCharArray())
        sslContext?.init(null, trustAllCerts, SecureRandom())
    } catch (e: IOException) {
        throw CertPathException(e.message)
    } catch (e: CertificateException) {
        throw CertPathException(e.message)
    } catch (e: NoSuchAlgorithmException) {
        throw CertPathException(e.message)
    } catch (e: UnrecoverableKeyException) {
        throw CertPathException(e.message)
    } catch (e: KeyStoreException) {
        throw CertPathException(e.message)
    } catch (e: KeyManagementException) {
        throw CertPathException(e.message)
    }

    companion object {
        @Throws(CertPathException::class)
        fun assembleImageLoading() {
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    override fun getAcceptedIssuers(): Array<X509Certificate?> {
                        return arrayOfNulls(0)
                    }

                    override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}
                    override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}
                })
                val sc = SSLContext.getInstance("SSL")
                sc.init(null, trustAllCerts, SecureRandom())
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
                HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
            } catch (e: Exception) {
                throw CertPathException(e.message)
            }
        }
    }

    init {
        assemble()
    }
}