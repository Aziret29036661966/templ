package com.example.template.adbSets

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.example.template.A_S_M_R
import com.example.template.A_S_M_R_FAIL
import com.example.template.CAMPAIGN
import com.example.template.MEDIA_SOURCE
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.gson.Gson
import eu.amirs.JSON
import io.michaelrocks.paranoid.Obfuscate
import kotlinx.coroutines.*
import java.util.ArrayList
import kotlin.coroutines.CoroutineContext

@Obfuscate
class TemplateAf(private val act: Activity) : AppsFlyerConversionListener, CoroutineScope {
    private val job = SupervisorJob()
    private val ioScope by lazy { CoroutineScope(job + Dispatchers.Main) }

    private var _testLiveData = MutableLiveData<List<Array<ArrayList<List<String>>>>>()
    val test = _testLiveData

    private var _adId = MutableLiveData<List<Array<ArrayList<List<String>>>>>()
    val adId = _adId

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onConversionDataSuccess(mapResult: MutableMap<String, Any>?) {
        if (ASMR.isNotEmpty()) {
            return
        }
        funcDesertCoroutine(mapResult)

    }

    override fun onConversionDataFail(p0: String?) {
        if (ASMRSECOND.isEmpty()) {
            return
        }
            ASMRSECOND = A_S_M_R_FAIL
            ioScope.launch {
                coroutineScope {
                    withContext(Dispatchers.IO) {
                        funcTrCatch()
                    }
                }
            }

    }
    fun funcDesertCoroutine(mapResult: MutableMap<String, Any>?){
        ASMR = A_S_M_R
        ioScope.launch {
            coroutineScope {
                withContext(Dispatchers.IO) {
                    var AIC: AdvertisingIdClient.Info =
                        AdvertisingIdClient.getAdvertisingIdInfo(act)
                    val ad = AIC.id.toString()
                    val mrDev = AppsFlyerLib.getInstance().getAppsFlyerUID(act)
                    withContext(Dispatchers.Main) {
                        try {
                            func(mapResult, ad, mrDev)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    fun func(mapResult: MutableMap<String,Any>?,
             ad:String,
             mrDev:String){
        _adId.value = listOf(arrayOf(arrayListOf(listOf(ad, mrDev))))
        val gson = gSon()
        val miniJsonForm = gson.toJson(mapResult)
        val miniApps = JSON(miniJsonForm)
        val firstResult = if(miniApps.jsonObject.has(CAMPAIGN))
            miniApps.jsonObject[CAMPAIGN].toString()
        else ""
        val secondResult = if(miniApps.jsonObject.has(MEDIA_SOURCE))
            miniApps.jsonObject[MEDIA_SOURCE].toString()
        else ""
        if (firstResult.isEmpty()){
            _testLiveData.value = listOf(arrayOf(arrayListOf(listOf("", ""))))
        } else {
            _testLiveData.value = listOf(
                arrayOf(arrayListOf(listOf(firstResult, secondResult))))
        }
    }

    fun funcTrCatch(){
        var missAdd: AdvertisingIdClient.Info? = null
        kotlin.runCatching {
            missAdd = AdvertisingIdClient.getAdvertisingIdInfo(act)
        }
        val myId = missAdd?.id.toString()
        val af = AppsFlyerLib.getInstance().getAppsFlyerUID(act)
        try {
            _adId.value = listOf(arrayOf(arrayListOf(listOf(myId, af))))
            _testLiveData.value = listOf(arrayOf(arrayListOf(listOf(""))))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}
    override fun onAttributionFailure(p0: String?) {}

    private fun gSon() : Gson = Gson()


    companion object {
        private var ASMR = ""
        private var ASMRSECOND = ""
    }
}