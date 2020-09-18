package eu.epfc.chuckfactstraining

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request

class HttpRequestService : IntentService("HttpRequestService"){

    companion object{
        val extraUrlKey = "key_intent_url"

        fun startActionRequestHttp(context: Context, url : String){
            val intent = Intent(context, HttpRequestService::class.java)
            intent.putExtra(extraUrlKey, url)
            context.startService(intent)
        }

    }

    override fun onHandleIntent(intent: Intent?) {

        if(intent != null){
            val urlString = intent.getStringExtra(extraUrlKey)
            val okHttpClient = OkHttpClient()
            val request = Request.Builder().url(urlString).build()
            val response = okHttpClient.newCall(request).execute()
            val responseString : String? = response.body()?.string()

            if(responseString != null){
               val intent =  Intent("httpRequestComplete")
                intent.putExtra("responseString", responseString)
                sendBroadcast(intent)
            }

        }

    }



}