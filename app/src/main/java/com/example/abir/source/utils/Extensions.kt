@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.abir.source.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.example.abir.source.BuildConfig
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

 fun View.addRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
    setBackgroundResource(resourceId)
}

 fun View.addCircleRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, this, true)
    setBackgroundResource(resourceId)
}


/*fun String.showToast() {
    Toast.makeText(MoneySenseApp.getApplicationContext(), this, Toast.LENGTH_SHORT).show()
}*/

const val APP_TAG = "Source"


fun String.logVerbose(tag: String = APP_TAG) {
    if (BuildConfig.DEBUG)
        Log.v(tag, this)
}

fun String.logDebug(tag: String = APP_TAG) {
    if (BuildConfig.DEBUG)
        Log.d(tag, this)
}

fun String.logInfo(tag: String = APP_TAG) {
    if (BuildConfig.DEBUG)
        Log.i(tag, this)
}

fun String.logWarn(tag: String = APP_TAG) {
    if (BuildConfig.DEBUG)
        Log.w(tag, this)

}

fun String.logError(tag: String = APP_TAG) {
    if (BuildConfig.DEBUG)
        Log.e(tag, this)
}

const val GIVEN_DATE_FORMAT = "dd MMMM yyyy hh:mm:ss aaa"
const val GIVEN_DATE_FORMAT_2 = "yyyy-MM-dd"
const val GIVEN_DATE_FORMAT_3 = "dd MMM yyyy"

const val REQUIRED_DATE_FORMAT = "dd MMM"
const val REQUIRED_DATE_FORMAT_2 = "dd MMM yyyy"
const val REQUIRED_DATE_FORMAT_3 = "EEE, dd MMM"
const val REQUIRED_DATE_FORMAT_4 = "MMM yyyy"

const val REQUIRED_TIME_FORMAT = "hh:mm aaa" // 12hr format
const val REQUIRED_TIME_FORMAT_2 = "kk:mm" // 24hr format


fun String.parseDateTimeWithFormat(
    givenFormat: String,
    requiredFormat: String = REQUIRED_DATE_FORMAT_2
): String {
    val inputFormat = SimpleDateFormat(givenFormat, Locale.ENGLISH)
    val outputFormat = SimpleDateFormat(requiredFormat, Locale.getDefault())

    return try {
        val dateValue: Date = inputFormat.parse(this)
        outputFormat.format(dateValue)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun Long.parseDateTimeWithFormat(requiredFormat: String = REQUIRED_DATE_FORMAT_2): String {
    return try {
        val date = Date(this)
        val format = SimpleDateFormat(requiredFormat, Locale.getDefault())
        format.format(date)
    } catch (e: Exception) {
        ""
    }
}

fun String.getDaysInterval(): String {
    val givenFormat = "yyyy-MM-dd"
    val inputFormat = SimpleDateFormat(givenFormat, Locale.ENGLISH)

    return try {
        val dateValue: Date = inputFormat.parse(this)
        val currentDate = Date()
        val diff: Long = dateValue.time - currentDate.time
        TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toString()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

// 1 means wifi
// 2 means mobile data
// 0 means no network
fun Context.checkNetworkStatus(): Int {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            ?: return 0
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network: Network? = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
            ?: return 0
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> 1
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> 2
            else -> 0
        }
    } else {
        connectivityManager.activeNetworkInfo?.let {
            return when (it.type) {
                ConnectivityManager.TYPE_WIFI -> 1
                ConnectivityManager.TYPE_MOBILE -> 2
                else -> 0
            }
        } ?: run {
            return 0
        }
    }
}

fun Activity.showSnackBarShort(text: CharSequence) {
    Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.showSnackBarShort(text: CharSequence) {
    this.activity?.let {
        Snackbar.make(it.findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
    }
}

fun Activity.showSnackBarLong(text: CharSequence) {
    Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG).show()
}

fun Fragment.showSnackBarLong(text: CharSequence) {
    this.activity?.let {
        Snackbar.make(it.findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG).show()
    }
}

fun Activity.showSnackBarShort(@StringRes text: Int) {
    Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.showSnackBarShort(@StringRes text: Int) {
    this.activity?.let {
        Snackbar.make(it.findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
    }
}