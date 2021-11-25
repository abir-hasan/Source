package com.example.abir.source.feature_extentions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.*
import androidx.core.app.ActivityCompat
import com.example.abir.source.MainActivity
import com.example.abir.source.utils.logDebug
import com.example.abir.source.utils.logError
import com.example.abir.source.utils.logInfo
import com.example.abir.source.utils.logWarn


private const val TAG = "MainActivity"

fun MainActivity.findDeviceModelManufacturerAndOS() {
    val status = StringBuilder()
        .append("\nOS Version: ${Build.VERSION.SDK_INT}")
        .append("\nMANUFACTURER: ${Build.MANUFACTURER}")
        .append("\nMODEL: ${Build.MODEL}")
    "findDeviceModelManufacturerAndOS() $status".logWarn(TAG)
}


/**
 * Needs Permission ACCESS_FINE_LOCATION
 * Also Device has to enable it's GPS to get all the data
 *
 * ASU - Arbitrary Strength Unit
 * LAC - Location Area Code
 * CI - Cell ID
 * RSSI - Received Signal Strength Indicator
 * RSRP - Reference Signal Received Power
 * RSRQ - Reference Signal Received Quality
 *
 * GSM is for 2G
 * CDMA WCDMA is for 3G
 * LTE is for 4G
 *
 * isRegistered indicates if a specific cell is active with the device
 */
fun MainActivity.getCellId() {
    try {
        val telephony = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        val cellLocation = telephony.allCellInfo
        if (cellLocation != null) {
            "getCellId() Cell Info Count: ${cellLocation.size}".logInfo(TAG)
            for (info in cellLocation) {
                when (info) {
                    is CellInfoGsm -> {
                        val identity: CellIdentityGsm = info.cellIdentity   //cellIdentity data
                        val defineTextLAC = identity.lac // Location Area Code
                        val cellId = identity.cid // Cell Id
                        val signalStrength = info.cellSignalStrength.dbm // Signal Strength
                        val isActive = info.isRegistered

                        val status = StringBuilder()
                            .append("Type: GSM-2G\n")
                            .append("Is Active: $isActive\n")
                            .append("Cell ID: $cellId\n")
                            .append("Signal Strength: $signalStrength\n")
                        "getCellId()\n$status".logWarn(TAG)
                    }
                    is CellInfoCdma -> {
                        val identity: CellIdentityCdma = info.cellIdentity   //cellIdentity data
                        val cellId = identity.basestationId // Cell Id
                        val signalStrength = info.cellSignalStrength.dbm // Signal Strength
                        val isActive = info.isRegistered

                        val status = StringBuilder()
                            .append("Type: CDMA 2G-3G\n")
                            .append("Is Active: $isActive\n")
                            .append("Cell ID: $cellId\n")
                            .append("Signal Strength: $signalStrength\n")
                        "getCellId()\n$status".logWarn(TAG)
                    }
                    is CellInfoWcdma -> {

                        val ecNo: Int? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            // Android API Level 30
                            info.cellSignalStrength.ecNo
                        } else {
                            null
                        }

                        val identity: CellIdentityWcdma = info.cellIdentity   //cellIdentity data
                        val cellId = identity.cid // Cell Id
                        val defineTextLAC = identity.lac // Location Area Code
                        val signalStrength = info.cellSignalStrength.dbm // Signal Strength
                        val level = info.cellSignalStrength.level
                        val asuLevel = info.cellSignalStrength.asuLevel
                        val isActive = info.isRegistered

                        val status = StringBuilder()
                            .append("Type: WCDMA 2G-3G\n")
                            .append("Is Active: $isActive\n")
                            .append("Cell ID: $cellId\n")
                            .append("Signal Strength: $signalStrength\n")
                        if (ecNo != null) {
                            status.append("EC/No: $ecNo")
                        }
                        "getCellId()\n$status".logDebug(TAG)
                    }
                    is CellInfoLte -> {
                        val rssi: Int? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            // Received Signal Strength Indication in dBm
                            info.cellSignalStrength.rssi
                        } else {
                            null
                        }
                        val rsrp: Int? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            info.cellSignalStrength.rsrp
                        } else {
                            null
                        }

                        val rsrq: Int? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            info.cellSignalStrength.rsrq
                        } else {
                            null
                        }

                        val rssnr: Int? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            // Reference Signal to Noise Ratio
                            info.cellSignalStrength.rssnr
                        } else {
                            null
                        }

                        val identity: CellIdentityLte = info.cellIdentity   //cellIdentity data
                        val cellId = identity.ci // Cell Id
                        val signalStrength = info.cellSignalStrength.dbm // Signal Strength
                        val level = info.cellSignalStrength.level
                        val asuLevel = info.cellSignalStrength.asuLevel
                        val isActive = info.isRegistered

                        val status = StringBuilder()
                            .append("Type: LTE 4G\n")
                            .append("Is Active: $isActive\n")
                            .append("Cell ID: $cellId\n")
                            .append("Signal Strength: $signalStrength\n")
                        if (rssi != null) {
                            status.append("RSSI: $rssi\n")
                        }
                        if (rsrp != null) {
                            status.append("RSRP: $rsrp\n")
                        }
                        if (rsrq != null) {
                            status.append("RSRQ: $rsrq\n")
                        }
                        if (rssnr != null) {
                            status.append("RSSNR: $rssnr")
                        }
                        "getCellId()\n$status".logDebug(TAG)
                    }
                }
            }
        } else {
            "getCellId() cell info list is null".logError(TAG)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        "getCellId() ${e.localizedMessage}".logError(TAG)
    }
}