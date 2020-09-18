package eu.epfc.trumpfacts

import android.app.IntentService
import android.content.Context
import android.content.Intent
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

class HTTPRequestService : IntentService("HttpRequestService") {


    companion object {
        val extraUrlKey = "key_intent_url"

        fun startActionRequestHttp(context: Context, url: String) {
            val intent = Intent(context, HTTPRequestService::class.java)
            intent.putExtra(extraUrlKey, url)
            context.startService(intent)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        //si on a bien reçu l'intent !!! attent)
        if (intent != null) {
            val urlString = intent.getStringExtra(extraUrlKey)

            val okHttpClient = OkHttpClient()//OkHttpClient()
            val request = Request.Builder().url(urlString).build()
            try{
                val response = okHttpClient.newCall(request).execute()//att la reponse du serveur
                val responseString: String? = response.body()?.string()

                //Ajoutez une ligne de code pour aﬃcher le résultat dans la console de log.
                if (responseString != null) {
                    val intent = Intent("HttpRequestComplete")
                    intent.putExtra("responseString", responseString)
                    sendBroadcast(intent)
                }
            }
            catch (e : Exception ){
                val intentFail = Intent("HttpRequestFail")
                sendBroadcast(intentFail)
            }
        }

    }

}