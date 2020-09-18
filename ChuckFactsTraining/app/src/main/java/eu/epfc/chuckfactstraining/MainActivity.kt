package eu.epfc.chuckfactstraining

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var urlTextView : TextView
    lateinit var factTextView: TextView



    inner class HttpReceiver : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent != null){
                val response = intent.getStringExtra("responseString")
                Log.d("MainActivity", response)

                val chuckNorris = JSONObject(response)
                val textToDisplay = chuckNorris.getString("value")
                // display the message in the textView
                factTextView.text = textToDisplay

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        urlTextView = findViewById(R.id.textUrl)
        factTextView = findViewById(R.id.text_chuck_fact)

        val httpReceiver = HttpReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction("httpRequestComplete")
        registerReceiver(httpReceiver, intentFilter)

    }


    fun onGetButtonClicked(view: View){
        val chuckUrl : String = "https://api.chucknorris.io/jokes/random"
        urlTextView.text = chuckUrl
        HttpRequestService.startActionRequestHttp(this.applicationContext, chuckUrl)
    }
}
