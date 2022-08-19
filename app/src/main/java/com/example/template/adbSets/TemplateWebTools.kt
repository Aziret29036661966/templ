package com.example.template.adbSets

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.webkit.*
import com.example.template.*
import io.michaelrocks.paranoid.Obfuscate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Obfuscate
object TemplateWebTools {
    suspend fun webInitStart(activity: Activity): WebViewClient = withContext(Dispatchers.Main) {
        val myTestClient = object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                request(request)
                return super.shouldInterceptRequest(view, request)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {
                if (url?.startsWith(T_ME) == true) {
                    activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
                return func(view, url)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                CookieManager.getInstance().flush()
            }
        }

        return@withContext myTestClient
    }
}