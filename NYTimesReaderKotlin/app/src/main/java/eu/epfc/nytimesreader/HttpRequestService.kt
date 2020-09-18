package eu.epfc.nytimesreader

import android.app.IntentService
import android.content.Context
import android.content.Intent
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class HttpRequestService : IntentService("HttpRequestService") {

    // static methods
    companion object{

        const val extraUrlKey = "key_intent_url"

        /**
         * Create and start a new service handling the http request
         * @param url : the url used by the http request
         */
        fun startActionRequestHttp(context: Context, url : String){
            val intent = Intent(context, HttpRequestService::class.java)
            intent.putExtra(extraUrlKey, url)
            context.startService(intent)
        }
    }

    /**
     * method called when the service is started, running on a background thread
     * @param intent : intent passed when the service has been created
     */
    override fun onHandleIntent(intent: Intent?) {

        if (intent != null) {
            val urlString = intent.getStringExtra(extraUrlKey)

            // Create http client
            val okHttpClient = OkHttpClient()
            // build a request
            val request = Request.Builder().url(urlString).build()
            // send the request
            try {
                val response = okHttpClient.newCall(request).execute()
                // extract data from the response
                val responseString : String? = response.body()?.string()

                if (responseString != null) {
                    // Broadcast a notification to main activity
                    val newIntent = Intent("httpRequestComplete")
                    newIntent.putExtra("responseString",responseString)
                    sendBroadcast(newIntent)
                }
            }
            catch (exception : IOException){
                val newIntent = Intent("httpRequestError")
                sendBroadcast(newIntent)
            }

        }
    }
}