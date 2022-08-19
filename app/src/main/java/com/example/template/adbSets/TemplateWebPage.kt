package com.example.template.adbSets

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.template.R
import com.orhanobut.hawk.Hawk
import io.michaelrocks.paranoid.Obfuscate
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@Obfuscate
class TemplateWebPage : AppCompatActivity(), CoroutineScope {
    private lateinit var myWebPart: WebView
    private var pathCallback: ValueCallback<Array<Uri>>? = null
    private val path: String? = null
    private var message: ValueCallback<Uri>? = null
    private val imageURI: Uri? = null
    private val chooser = 1
    private val job = SupervisorJob()
    private val ioScope by lazy { CoroutineScope(job + Dispatchers.Main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        myWebPart = findViewById(R.id.webView)
        supportActionBar?.hide()
        ioScope.launch {
            withContext(coroutineContext) {
                myWebPart.webViewClient = TemplateWebTools.webInitStart(this@TemplateWebPage)
                try {
                    TemplateWebSet.mainWebSet(
                        listOf(arrayOf(myWebPart)),
                        listOf(true),
                        listOf(false),
                        listOf(0),
                        listOf("; wv")
                    )
                    myWebPart.webChromeClient = object : WebChromeClient() {
                        override fun onShowFileChooser(
                            view: WebView?,
                            filePath: ValueCallback<Array<Uri>>?,
                            fileChooserParams: FileChooserParams?
                        ): Boolean {
                            pathCallback?.onReceiveValue(null)
                            pathCallback = filePath
                            val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
                            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
                            contentSelectionIntent.type = "*/*"
                            val intentArray = arrayOfNulls<Intent>(0)
                            val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
                            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Select Option:")
                            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
                            startActivityForResult(chooserIntent, 1)
                            return true
                        }
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }

                val link = Hawk.get<String>("result")
                Log.d("linkSaved", link)
                myWebPart.loadUrl(link)
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        myWebPart.restoreState(savedInstanceState)
    }

    override fun onSaveInstanceState(
        outState: Bundle,
        outPersistentState: PersistableBundle
    ) {
        super.onSaveInstanceState(outState, outPersistentState)
        myWebPart.saveState(outState)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        val controller = WindowInsetsControllerCompat(
            window,
            window.decorView
        )
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat
                .BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller
            .hide(
                WindowInsetsCompat
                    .Type
                    .systemBars()
            )
        super.onWindowFocusChanged(hasFocus)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        myWebPart.saveState(outState)

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (pathCallback == null) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }
        var results: Array<Uri>? = null
        if (resultCode == RESULT_OK) {
            if (data == null) {
                if (path != null) {
                    results = arrayOf(Uri.parse(path))
                }
            } else {
                val dataString: String? = data.dataString
                if (dataString != null) {
                    results = arrayOf(Uri.parse(dataString))
                }
            }
        }

        pathCallback!!.onReceiveValue(results)
        pathCallback = null


        if (requestCode != chooser || message == null) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }
        var result: Uri? = null
        try {
            result = if (resultCode != RESULT_OK) {
                null
            } else {
                if (data == null) imageURI else data.data
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        message!!.onReceiveValue(result)
        message = null
    }

    override fun onBackPressed() {
        if (myWebPart.canGoBack()) {
            myWebPart.goBack()
        }
    }

}