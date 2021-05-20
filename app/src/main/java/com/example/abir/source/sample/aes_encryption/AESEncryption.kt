package com.example.abir.source.sample.aes_encryption

import android.util.Base64
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AESEncryption {

    companion object {
        private const val TAG = "AESEncryption"
    }

    private val charsetUtf8 = StandardCharsets.UTF_8
    private val secretKeyPlain = "abir-dUmmy-#2021".toByteArray(StandardCharsets.UTF_8)


    // Standard AES
    private val encryptionMethod = "AES"
    // Standard AES

    // Standard AES/CBC/PKCS5PADDING
    private val encryptionMethodAESCBC = "AES/CBC/PKCS5PADDING"
    // Standard AES/CBC/PKCS5PADDING


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

    @Throws(Exception::class)
    fun decryptMessageWithCBCMethod(textToDecrypt: String): String {
        val sKeySpec = SecretKeySpec(secretKeyPlain, encryptionMethodAESCBC)
        val cipher: Cipher = Cipher.getInstance(encryptionMethodAESCBC)
        val initVector = ByteArray(16) // required for CBC
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec, IvParameterSpec(initVector))

        val encryptedBytes: ByteArray = Base64.decode(textToDecrypt, Base64.URL_SAFE)
        val decrypted: ByteArray = cipher.doFinal(encryptedBytes)
        return String(decrypted, charsetUtf8)
    }

    @Throws(Exception::class)
    fun encryptMessageWithCBCMethod(textToEncrypt: String): String {
        val fileData = textToEncrypt.toByteArray(charsetUtf8)

        val sKeySpec = SecretKeySpec(secretKeyPlain, encryptionMethodAESCBC)
        val cipher = Cipher.getInstance(encryptionMethodAESCBC)
        val initVector = ByteArray(16) // required for CBC
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, IvParameterSpec(initVector))

        val encrypted = cipher.doFinal(fileData)
        return Base64.encodeToString(encrypted, Base64.URL_SAFE)
    }
}