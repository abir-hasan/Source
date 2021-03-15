package com.example.abir.source.feature_extentions

import android.content.ComponentName
import android.net.Uri
import androidx.browser.customtabs.*
import androidx.core.content.ContextCompat
import com.example.abir.source.MainActivity
import com.example.abir.source.R
import com.example.abir.source.utils.logError

fun MainActivity.prepareCustomTab(url: String) {

    var mClient: CustomTabsClient? = null
    var mCustomTabsSession: CustomTabsSession? = null

    val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()

    val params = CustomTabColorSchemeParams.Builder()
    params.setToolbarColor(ContextCompat.getColor(this, R.color.purple500))
    builder.setDefaultColorSchemeParams(params.build())

    builder.setShowTitle(true)
    // val icon = BitmapFactory.decodeResource(resources, R.drawable.ic_dashboard_black_24dp)
    //PendingIntent pendingIntent = createPendingShareIntent(url);
    //builder.setActionButton(icon, getString(R.string.app_name), pendingIntent, true);
    //builder.addMenuItem("Share this page", pendingIntent);
    val mCustomTabsServiceConnection: CustomTabsServiceConnection =
        object : CustomTabsServiceConnection() {
            override fun onCustomTabsServiceConnected(
                componentName: ComponentName,
                customTabsClient: CustomTabsClient
            ) {
                //Pre-warming
                mClient = customTabsClient
                mClient?.warmup(0L)
                mCustomTabsSession = mClient?.newSession(null)
                mCustomTabsSession!!.mayLaunchUrl(Uri.parse(url), null, null)
            }

            override fun onServiceDisconnected(name: ComponentName) {
                "onServiceDisconnected() called with: name = $name".logError()
                mClient = null
            }
        }
    val warmedUp = CustomTabsClient.bindCustomTabsService(
        this.applicationContext,
        "com.android.chrome", mCustomTabsServiceConnection
    )
    builder.build().launchUrl(this, Uri.parse(url))
}