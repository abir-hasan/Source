package com.example.abir.source.sample.aes_encryption

import android.util.Base64
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class AESEncryption {

    companion object {
        private const val TAG = "AESEncryption"
    }

    private val secretKeyPlain = "abir-dUmmy-#2021".toByteArray(StandardCharsets.UTF_8)
    private val charsetUtf8 = StandardCharsets.UTF_8
    private val encryptionMethod = "AES"


    @Throws(Exception::class)
    fun decryptMessage(textToDecrypt: String): String {
        val sKeySpec = SecretKeySpec(secretKeyPlain, encryptionMethod)
        val cipher: Cipher = Cipher.getInstance(encryptionMethod)
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec)

        val encryptedBytes: ByteArray = Base64.decode(textToDecrypt, Base64.DEFAULT)
        val decrypted: ByteArray = cipher.doFinal(encryptedBytes)
        return String(decrypted, charsetUtf8)
    }

    @Throws(Exception::class)
    fun encryptMessage(textToEncrypt: String): String {
        val fileData = textToEncrypt.toByteArray(charsetUtf8)

        val sKeySpec = SecretKeySpec(secretKeyPlain, encryptionMethod)
        val cipher = Cipher.getInstance(encryptionMethod)
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec)

        val encrypted = cipher.doFinal(fileData)
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }
}