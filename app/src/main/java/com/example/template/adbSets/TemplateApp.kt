package com.example.template.adbSets

import android.app.Application
import android.content.Context
import com.cioccarellia.ksprefs.KsPrefs
import com.example.template.SIGNAL_KEY
import com.onesignal.OneSignal
import com.orhanobut.hawk.Hawk

class TemplateApp : Application() {
    private val ONE_SIGNAL_KEY = SIGNAL_KEY

    companion object {
        lateinit var appContext: Context
        val prefs by lazy { KsPrefs(appContext) }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        Hawk.init(this).build()
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONE_SIGNAL_KEY)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
    }
}