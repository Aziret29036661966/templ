package com.example.template.adbSets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.provider.Settings
import com.android.volley.Request.Method.GET
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.template.*
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.gson.Gson
import com.onesignal.OneSignal
import com.orhanobut.hawk.Hawk
import eu.amirs.JSON
import io.michaelrocks.paranoid.Obfuscate
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.ArrayList
import kotlin.coroutines.CoroutineContext


@Obfuscate
class TemplateMainLogic : AppCompatActivity(), CoroutineScope {
    private val job = SupervisorJob()
    private val ioScope by lazy { CoroutineScope(job + Dispatchers.Main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beat_loading)
        supportActionBar?.hide()
        hawk()
    }

    private fun secondNetworkChecking() {
        ioScope.launch {
            withContext(Dispatchers.Main) {
                withContext(Dispatchers.Main) {
                   secondTrCatch()
                }
            }

        }
    }

    private fun funcBig(
        campaign: List<Array<ArrayList<List<String>>>>,
        loads: JSON,
        ads: List<Array<ArrayList<List<String>>>>,
        devSets: Int
    ) {
             forFuncBig(campaign, loads, ads, devSets, this, finish())
    }

    private fun funcFour(
        loads: JSON,
        data: List<Array<ArrayList<List<String>>>>,
        ads: List<Array<ArrayList<List<String>>>>,
        devSets: Int
    ) {
       forFuncFour(loads, data, ads, devSets, this, finish())
    }

    private fun funcThree(
        campaign: List<Array<ArrayList<List<String>>>>,
        loads: JSON,
        data: List<Array<ArrayList<List<String>>>>,
        ads: List<Array<ArrayList<List<String>>>>,
        devSets: Int
    ) {
        forFuncThree(campaign, loads, data, ads, devSets,this, finish())
    }

    private fun funcTwo(
        loads: JSON,
        data: List<Array<ArrayList<List<String>>>>,
        ads: List<Array<ArrayList<List<String>>>>
    ) {
        forFuncTwo(loads, data, ads,this, finish())
    }

    private fun funcOne(campaign: List<Array<ArrayList<List<String>>>>) {
        forFuncOne(campaign)
    }

    private fun mainChecking() {
        if (Hawk.get<String>(RESULT) == GAME) {
            startActivity(
                Intent(
                    this@TemplateMainLogic,
                    MainActivity::class.java
                )
            )
            finish()
        } else {
            startActivity(
                Intent(
                    this@TemplateMainLogic,
                    TemplateWebPage::class.java
                )
            )
            finish()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO


    private fun hawk(){
        if (Hawk.contains(RESULT)) {
            mainChecking()
        } else {
            secondNetworkChecking()
        }
    }

    private fun facebook() {
        val url = RETRY
        StringRequest(
            GET, url,
          { success ->
                val loads = JSON(success)
                Gson()
        FacebookSdk.setApplicationId(loads.jsonObject[F_B].toString())
        FacebookSdk.setAdvertiserIDCollectionEnabled(true)
        FacebookSdk.sdkInitialize(this@TemplateMainLogic)
        FacebookSdk.fullyInitialize()
        AppEventsLogger.activateApp(this@TemplateMainLogic)
          }, {
            })
    }

    private fun secondTrCatch(){
        try {
            val queue = Volley.newRequestQueue(this@TemplateMainLogic)
            val url = RETRY
            val stringRequest = StringRequest(
                GET, url,
                { success ->
                    JSON(success)
                    Gson()
                    facebook()
                    ioScope.launch {
                        withContext(Dispatchers.Main) {
                            funcCont()
                        }
                    }
                }, {
                })
            queue.add(stringRequest)


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun funcCont() {
        val url = RETRY
        StringRequest(
            GET, url,
            { success ->
                val loads = JSON(success)
                Gson()
                    val devSets = Settings.Secure.getInt(
                        this@TemplateMainLogic.contentResolver,
                        DEVELOPMENT,
                        0
                    )
                    val templateDeepTools = TemplateDeepTools()
                    templateDeepTools.engine(this@TemplateMainLogic)
                    templateDeepTools.test.observe(this@TemplateMainLogic) { data ->
                        val frozenAf = TemplateAf(this@TemplateMainLogic)
                        launchApps(this@TemplateMainLogic, frozenAf)
                        frozenAf.adId.observe(this@TemplateMainLogic) { ads ->
                            frozenAf.test.observe(this@TemplateMainLogic) { campaign ->
                                funcOne(campaign)
                                when (data[0][0][0][0].isNotEmpty()) {
                                    campaign[0][0][0][0].isNotEmpty()
                                    -> funcTwo(loads, data, ads)
                                    campaign[0][0][0][1].isNotEmpty() ->
                                        funcThree(
                                            campaign,
                                            loads,
                                            data,
                                            ads,
                                            devSets
                                        )
                                    campaign[0][0][0][1].isEmpty() ->
                                        funcFour(loads, data, ads, devSets)
                                    else -> {
                                        funcBig(campaign, loads, ads, devSets)
                                    }
                                }
                            }
                        }

                    }
            }, {

            })
    }
}