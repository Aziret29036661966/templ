package com.example.template.adbSets

import android.app.Activity
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.example.template.KEY
import com.example.template.MY_APP

fun launchApps(activity: Activity, cosmicApps: AppsFlyerConversionListener) {
    AppsFlyerLib.getInstance().init(KEY, cosmicApps, activity)
    AppsFlyerLib.getInstance().start(activity)
}

fun getApp() = MY_APP
fun getNull(): Any? = null
fun getTrue () = true