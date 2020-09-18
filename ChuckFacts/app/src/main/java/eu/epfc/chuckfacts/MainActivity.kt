package eu.epfc.chuckfacts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var spinner : Spinner //liste de catégorie (array)
    lateinit var urlTextView : TextView
    lateinit var factTextView : TextView


    inner class HttpReceiver : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {

            if(intent != null){
                // si l'intent action est = à httpResquestComplete
                val response :String?  = intent.getStringExtra("responseString")
                Log.d("MainActivity",response)

                val chuckNorris = JSONObject(response)
                // a voir dans le serveur du dev mobile
                val textToDisplay = chuckNorris.getString("value")
                factTextView.text = textToDisplay
                //setBackroungColor white
            }//else
            //texteFact.text = "Error : no Connexion"
            ////setBackroungColor red
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.menu_categories)
        urlTextView = findViewById(R.id.text_url_display)
        factTextView = findViewById(R.id.text_chuck_facts)

        //créer une réception
        val httpReceiver = HttpReceiver()
        // créer un filtre
        val intentFilter = IntentFilter()
        intentFilter.addAction("HttpRequestComplete")
        // commencer à utiliser le récepteur avec le filtre
        registerReceiver(httpReceiver, intentFilter)

    }

    fun onGetButtonClicked (view : View){ //call back de get New back
        val category = spinner.selectedItem.toString()
        val chuckUrl = "https://api.chucknorris.io/jokes/random?category=$category"
        urlTextView.text = chuckUrl
        HttpRequestService.startActionRequestHttp(this.applicationContext,chuckUrl)

        // val chuckUrl : String = "https://api.chucknorris.io/jokes/random?category=mycategory"
        //context (activite) est une l'etat courant de l'appli et boite à outil d'acces au ressources
        //HttpRequestService.startActionRequestHttp(context = this.applicationContext, url = chuckUrl)
    }

}
