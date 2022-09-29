package com.example.healthcareapp.util

import android.content.Context
import com.example.healthcareapp.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.io.InputStream
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.inject.Inject
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

class CertificateHelper2 @Inject constructor(
    @ApplicationContext context: Context
) {
    lateinit var tmf: TrustManagerFactory
    lateinit var sslContext: SSLContext

    init {
        val cf: CertificateFactory
        val ca: Certificate

        val caInput: InputStream

        try {
            cf = CertificateFactory.getInstance("X.509")

            caInput = context.resources.openRawResource(R.raw.digicertsha2562020ca1)


            ca = cf.generateCertificate(caInput)
            println("ca = ${(ca as X509Certificate).subjectDN}")

            val keyStoreType = KeyStore.getDefaultType()
            val keyStore = KeyStore.getInstance(keyStoreType)
            with(keyStore) {
                load(null, null)
                keyStore.setCertificateEntry("ca", ca)
            }

            val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
            tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
            tmf.init(keyStore)

            sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, tmf.trustManagers, java.security.SecureRandom())

            caInput.close()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
