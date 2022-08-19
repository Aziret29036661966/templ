package com.example.template.adbSets

import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
object TemplateWebSet {

    fun mainWebSet(listResult: List<Array<WebView>>, listBulls: List<Boolean>, listBullsSecondary: List<Boolean>, listNum: List<Int>, listStrokes: List<String>) {
        firstPartOfSet(listResult, listBulls)
        listResult[0][0].settings.saveFormData = listBulls[0]
        listResult[0][0].settings.databaseEnabled = listBulls[0]
        secondPartOfSet(listResult, listBulls, listBullsSecondary, listStrokes)
        val instance = CookieManager.getInstance()
        instance.setAcceptCookie(listBulls[0])
        listResult[0][0].isFocusable = listBulls[0]
        listResult[0][0].isFocusableInTouchMode = listBulls[0]
        listResult[0][0].isSaveEnabled = listBulls[0]
        CookieManager.getInstance().setAcceptThirdPartyCookies(listResult[0][0], listBulls[0])
        listResult[0][0].isSaveEnabled = listBulls[0]
        listResult[0][0].settings.cacheMode = WebSettings.LOAD_DEFAULT
        listResult[0][0].settings.mixedContentMode = listNum[0]
        thirdPartOfSet(listResult, listBulls)
    }

    private fun thirdPartOfSet(list: List<Array<WebView>>, list2: List<Boolean>) {
        list[0][0].settings.allowFileAccess = list2[0]
        list[0][0].settings.allowFileAccessFromFileURLs = list2[0]
        list[0][0].settings.allowUniversalAccessFromFileURLs = list2[0]
        list[0][0].settings.useWideViewPort = list2[0]
        list[0][0].settings.loadWithOverviewMode = list2[0]
    }

    private fun secondPartOfSet(
        list: List<Array<WebView>>,
        list1: List<Boolean>,
        list2: List<Boolean>,
        list3: List<String>
    ) {
        list[0][0].settings.setSupportZoom(list2[0])
        list[0][0].settings.javaScriptCanOpenWindowsAutomatically = list1[0]
        list[0][0].settings.loadsImagesAutomatically = list1[0]
        list[0][0].settings.userAgentString = list[0][0].settings.userAgentString.replace(list3[0], "")
    }

    private fun firstPartOfSet(list: List<Array<WebView>>, list1: List<Boolean>) {
        list[0][0].settings.pluginState = WebSettings.PluginState.ON
        list[0][0].settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        list[0][0].settings.allowContentAccess = list1[0]
        list[0][0].settings.setEnableSmoothTransition(list1[0])
        list[0][0].settings.setAppCacheEnabled(list1[0])
        list[0][0].settings.domStorageEnabled = list1[0]
        list[0][0].settings.javaScriptEnabled = list1[0]
        list[0][0].settings.savePassword = list1[0]
    }
}