package com.example.abir.source.sample.jwt_token_example

import com.example.abir.source.utils.logVerbose
import com.example.abir.source.utils.logWarn
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.nio.charset.StandardCharsets
import java.security.Key
import javax.crypto.spec.SecretKeySpec


class JWTSample {

    companion object {
        private const val TAG = "JWTSample"
    }

    private val secretKeyPlain =
        "abir-dUmmy-#2021-pAsSwOrD-dhaKa7".toByteArray(StandardCharsets.UTF_8)

    private val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256

    private val claimKey = "token"

    fun decryptMessage(textToDecrypt: String): String {
        return try {
            val signingKey: Key = SecretKeySpec(secretKeyPlain, signatureAlgorithm.jcaName)

            val claim: Claims = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(textToDecrypt)
                .body
            "decryptMessage() claim: $claim".logVerbose(TAG)
            val decryptedMessage: String = claim[claimKey].toString()
            "decryptMessage() value: $decryptedMessage".logWarn(TAG)
            decryptedMessage
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun encryptMessage(textToEncrypt: String): String {
        val jwt = Jwts.builder()
            .claim(claimKey, textToEncrypt)
            .signWith(signatureAlgorithm, secretKeyPlain)
            .compact()
        "encryptMessage() JWT: $jwt".logWarn(TAG)
        return jwt
    }

}
