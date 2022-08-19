package com.example.template.adbSets

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.facebook.applinks.AppLinkData
import io.michaelrocks.paranoid.Obfuscate
import kotlinx.coroutines.*
import java.util.*

@Obfuscate
class TemplateDeepTools {
    private val job = SupervisorJob()
    private val ioScope by lazy { CoroutineScope(job + Dispatchers.Main) }

    private var _testLiveData = MutableLiveData<List<Array<ArrayList<List<String>>>>>()
    val test = _testLiveData

    private var myFin: String? = ""

    fun engine(ACTIVITY: Activity) {
        ioScope.launch {
            checkEngineParts(ACTIVITY, ::getApp, ::getNull, ::getTrue)
        }
    }

    private fun checkEngineParts(
        act: Activity,
        myapp: () -> String,
        returnNull: () -> Any?,
        returnTrue: () -> Boolean
    ) { AppLinkData.fetchDeferredAppLinkData(act) { paramAnonymousAppLinkData ->
        if (paramAnonymousAppLinkData != returnNull() || AppLinkData.createFromActivity(act)
            != returnNull) { trCatch(act, myapp) }
        if (!myFin.equals("", ignoreCase = returnTrue())) {
                isScope(act)
        } else { funcNothing(act) } }
        }

    private fun funcNothing(act: Activity) {
        ioScope.launch {
            withContext(Dispatchers.Main) {
                _testLiveData.value = listOf(arrayOf(arrayListOf(listOf(""))))
                engine(act)
            }
        }
    }

    private fun trCatch(
        act: Activity,
        myapp: () -> String
    ) {
        AppLinkData.fetchDeferredAppLinkData(act) { paramAnonymousAppLinkData ->
            try {
                myFin = Objects.requireNonNull(paramAnonymousAppLinkData!!.targetUri)
                    .toString()
                    .replace(myapp(), "")
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }
    }
    private fun isScope(act: Activity) {
        myFin?.let {
            ioScope.launch {
                withContext(Dispatchers.Main) {
                    _testLiveData.value = listOf(arrayOf(arrayListOf(listOf(it))))
                    engine(act)
                }
            }
        }
    }
}