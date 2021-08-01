package com.example.abir.source.utils

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import com.example.abir.source.MainActivity


fun MainActivity.openFbMessengerOfAUser(userId: String = "186202548075080") {
    try {
            // R - 189729221039415
            // A - 186202548075080
        val messengerUrl: String = if (
            isAppInstalled("com.facebook.orca") ||
            isAppInstalled("com.facebook.mlite")
        ) {
            "fb-messenger://user/$userId/"
        } else {
            Toast.makeText(
                this,
                "FB-Messenger is not installed , open Messenger in browser",
                Toast.LENGTH_SHORT
            ).show()
            "https://www.messenger.com/t/$userId/"
        }
        val messengerIntent = Intent(Intent.ACTION_VIEW)
        messengerIntent.data = Uri.parse(messengerUrl)
        startActivity(messengerIntent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun MainActivity.isAppInstalled(packageName: String): Boolean {
    return try {
        this.applicationContext.packageManager.getApplicationInfo(
            // Make sure to put this in manifest queries for Android 11(API-30) and above
            // Doc: https://developer.android.com/training/package-visibility
            packageName,
            0
        )
        true
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        false
    }
}