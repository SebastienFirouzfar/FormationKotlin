package eu.epfc.chuckfacts

import android.app.IntentService
import android.content.Context
import android.content.Intent
import okhttp3.OkHttpClient
import okhttp3.Request



class HttpRequestService  : IntentService("HttpRequestService"){

    //static method
    companion object{
        val extraUrlKey = "key_intent_url"

        fun startActionRequestHttp(context: Context, url : String){
            val intent = Intent(context, HttpRequestService::class.java)
            intent.putExtra(extraUrlKey, url)
            context.startService(intent)
        }
    }
    
    // faire un try catch avec ex : exception et mettre response, responseString et la condition responseString dans le try
    //dans le catch on met le intent avec l'action de HttpRequestFail
    override fun onHandleIntent(intent: Intent?) {
        //si on a bien reçu l'intent !!! attention le ? est nullable
        if (intent != null) {
            val urlString = intent.getStringExtra(extraUrlKey)

            val okHttpClient = OkHttpClient()
            val request = Request.Builder().url(urlString).build()
            val response = okHttpClient.newCall(request).execute()//att la reponse du serveur
            val responseString: String? = response.body()?.string()

            //Ajoutez une ligne de code pour aﬃcher le résultat dans la console de log.
            if(responseString != null){

                val intent = Intent("HttpRequestComplete")
                intent.putExtra("responseString",responseString)
                sendBroadcast(intent)

                //Log.d("HttpRequestService", reponseString)
            }
        }

    }
}