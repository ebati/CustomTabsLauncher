package com.droibit.android.customtabs.launcher.internal

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import com.droibit.android.customtabs.launcher.CustomTabsFallback

internal class CustomTabsLauncherImpl {
    fun launch(
        context: Context,
        customTabsIntent: CustomTabsIntent,
        uri: Uri,
        expectCustomTabsPackages: List<String>,
        fallback: CustomTabsFallback?
    ) {
        var customTabsPackage = CustomTabsClient.getPackageName(context, expectCustomTabsPackages, true)
        if (customTabsPackage == null && fallback != null) {
            fallback.openUrl(context, uri, customTabsIntent)
            return
        }
        if (customTabsPackage == null) {
            customTabsPackage =
                expectCustomTabsPackages.firstOrNull() ?: CustomTabsPackage.CHROME_PACKAGES.first()
        }
        customTabsIntent.intent.setPackage(customTabsPackage)
        customTabsIntent.launchUrl(context, uri)
    }
}