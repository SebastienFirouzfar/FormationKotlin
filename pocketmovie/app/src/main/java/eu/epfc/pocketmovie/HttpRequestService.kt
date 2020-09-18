package eu.epfc.pocketmovie

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class HttpRequestService:IntentService(NAME_SERVICE) {

    companion object{

        const val NAME_SERVICE  = "HTTP_REQUEST_SERVICE"
        const val HTTP_REQUEST_COMPLETE = "HTTP_REQUEST_COMPLETE"
        const val HTTP_REQUEST_ERROR = "HTTP_REQUEST_ERROR"
        const val HTTP_RESPONSE_STRING = "HTTP_RESPONSE_STRING"
        const val HTTP_URL_STRING = "HTTP_URL_STRING"
        const val HTTP_REQUEST_RESULT = "HTTP_REQUEST_RESULT"


        fun startActionRequestHttp(context: Context, urlString:String,resultComplete:String,resultError:String){
            val intent = Intent(context, HttpRequestService::class.java)

            intent.putExtra(HTTP_URL_STRING,urlString)
            intent.putExtra(HTTP_REQUEST_COMPLETE,resultComplete)
            intent.putExtra(HTTP_REQUEST_ERROR,resultError)

            context.startService(intent)
        }
    }



    override fun onHandleIntent(intent: Intent?) {

        if (intent != null) {
            val urlString = intent.getStringExtra(HTTP_URL_STRING)
            val resultComplete= intent.getStringExtra(HTTP_REQUEST_COMPLETE)
            val resultError= intent.getStringExtra(HTTP_REQUEST_ERROR)


            // Create http client
            val okHttpClient = OkHttpClient()
            // build a request
            val request = Request.Builder().url(urlString).build()
            // send the request
            try {
                val response = okHttpClient.newCall(request).execute()

                if (response.code() == 200){

                }


                // extract data from the response
                val responseString : String? = response.body()?.string()

                if (responseString != null) {
                    // Broadcast a notification to called object
                    val newIntent = Intent(resultComplete)
                    newIntent.putExtra(HTTP_RESPONSE_STRING,responseString)

                    sendBroadcast(newIntent)
                }

            }
            catch (exception : IOException){
                val newIntent = Intent(resultError)
                sendBroadcast(newIntent)
            }

        }
    }
}