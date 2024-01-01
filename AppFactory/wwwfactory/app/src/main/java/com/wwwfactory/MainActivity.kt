package com.wwwfactory

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val builder = CustomTabsIntent.Builder()
        builder.setInstantAppsEnabled(true)
        val customBuilder = builder.build()
        customBuilder.intent.setPackage("com.android.chrome")
        customBuilder.launchUrl(this, Uri.parse("https://www.google.com"))
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        finishAndRemoveTask();
    }

}