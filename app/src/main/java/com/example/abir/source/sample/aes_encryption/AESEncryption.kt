package com.example.abir.source.sample.aes_encryption

import android.util.Base64
import com.example.abir.source.utils.logDebug
import java.nio.charset.StandardCharsets
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.security.spec.KeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class AESEncryption {

    companion object {
        private const val TAG = "AESEncryption"
    }


    private val secretKeyPlain = "abir-dUmmy-#2021".toByteArray(StandardCharsets.UTF_8)

    private val pswdIterations = 10
    private val keySize = 128

    //private val cypherInstance = "AES/CBC/PKCS5Padding"
    private val cypherInstance = "AES"
    private val secretKeyInstance = "PBKDF2WithHmacSHA1"
    private val plainText = "sampleText"
    private val AESSalt = "exampleSalt"
    private val initializationVector = "8119745113154120"

    private val charsetUtf8 = StandardCharsets.UTF_8

    @Throws(Exception::class)
    fun decryptV2(textToDecrypt: String = "U2FsdGVkX19tbVpitvZR+e2BoiZsE49oqLMg/K2y4v8="): String? {
        /*val skeySpec = SecretKeySpec(
            Arrays.copyOf(secretKeyPlain.toByteArray(charsetUtf8), 16),
            "AES"
        )*/
        val skeySpec = SecretKeySpec(
            secretKeyPlain,
            "AES"
        )
        /*val skeySpec = SecretKeySpec(
            Base64.decode(secretKeyPlain, Base64.DEFAULT),
            "AES"
        )*/

        // val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val cipher: Cipher = Cipher.getInstance("AES")
        cipher.init(
            Cipher.DECRYPT_MODE,
            skeySpec
        )

        val encryted_bytes: ByteArray = Base64.decode(textToDecrypt, Base64.DEFAULT)
        //val encryted_bytes: ByteArray = textToDecrypt.toByteArray(charsetUtf8)
        /*val encryted_bytes: ByteArray = Arrays.copyOf(
            textToDecrypt.toByteArray(charsetUtf8), 16
        )*/


        /*val decrypted: ByteArray = cipher.doFinal(
            org.apache.commons.codec.binary.Base64.decodeBase64(
                textToDecrypt.toByteArray()
            )
        )*/
        val decrypted: ByteArray = cipher.doFinal(
            encryted_bytes
        )
        return String(decrypted, charsetUtf8)
    }

    @Throws(Exception::class)
    fun encryptV2(textToEncrypt: String): String {
        val fileData = textToEncrypt.toByteArray(charsetUtf8)

        val skeySpec = SecretKeySpec(secretKeyPlain, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec)

        val encrypted = cipher.doFinal(fileData)
        //val encryptedMessage = String(encrypted, charsetUtf8)
        val encryptedMessage = Base64.encodeToString(encrypted,Base64.DEFAULT)
        "encryptV2() message: $encryptedMessage".logDebug(TAG)
        return encryptedMessage
    }


    /*@Throws(Exception::class)
    fun encrypt(textToEncrypt: String, secretKey: String): String? {
        val skeySpec = SecretKeySpec(getRaw(plainText, AESSalt), "AES")
        val cipher: Cipher = Cipher.getInstance(cypherInstance)
        cipher.init(
            Cipher.ENCRYPT_MODE,
            skeySpec,
            IvParameterSpec(initializationVector.toByteArray())
        )
        val encrypted: ByteArray = cipher.doFinal(textToEncrypt.toByteArray())
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }


    @Throws(java.lang.Exception::class)
    fun decrypt(textToDecrypt: String?): String? {
        val encryted_bytes = Base64.decode(textToDecrypt, Base64.DEFAULT)
        val skeySpec = SecretKeySpec(getRaw(plainText, AESSalt), "AES")
        val cipher = Cipher.getInstance(cypherInstance)
        cipher.init(
            Cipher.DECRYPT_MODE,
            skeySpec,
            IvParameterSpec(initializationVector.toByteArray())
        )
        val decrypted = cipher.doFinal(encryted_bytes)
        return String(decrypted, charsetUtf8)
    }

    private fun getRaw(plainText: String, salt: String): ByteArray? {
        try {
            val factory: SecretKeyFactory = SecretKeyFactory.getInstance(secretKeyInstance)
            val spec: KeySpec =
                PBEKeySpec(plainText.toCharArray(), salt.toByteArray(), pswdIterations, keySize)
            return factory.generateSecret(spec).getEncoded()
        } catch (e: InvalidKeySpecException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ByteArray(0)
    }*/
}