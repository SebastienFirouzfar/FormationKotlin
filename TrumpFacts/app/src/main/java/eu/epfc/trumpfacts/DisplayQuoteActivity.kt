package eu.epfc.trumpfacts

import android.animation.Animator
import android.animation.AnimatorSet
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import org.json.JSONObject
import java.lang.Exception
import android.animation.ObjectAnimator
import org.json.JSONException

class DisplayQuoteActivity : AppCompatActivity() {
    lateinit var textViewQuote : TextView
    private lateinit var name: String
    private val baseURL = "https://api.whatdoestrumpthink.com/api/v1/"
    private val trumpPersonalizedQuoteURL = "quotes/personalized?q="
    private lateinit var getNewQuoteButton: Button
    private lateinit var  maskedProgressBar : ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_quote)

        //créer une réception
        val httpReceiver = HttpReceiver()
        // créer un filtre
        val intentFilter = IntentFilter()
        intentFilter.addAction("HttpRequestComplete")
        // commencer à utiliser le récepteur avec le filtre
        registerReceiver(httpReceiver, intentFilter)

        textViewQuote = findViewById(R.id.textQuote)
        name = intent.getStringExtra(Intent.EXTRA_TEXT)


        getNewQuoteButton = findViewById(R.id.quote_button)
        maskedProgressBar = findViewById(R.id.progress_bar)

        //button version lambda Get new fact
        getNewQuoteButton.setOnClickListener {
            //val url = "https://api.whatdoestrumpthink.com/api/v1/quotes/random"
            val url = getURLFromName(name)
            HTTPRequestService.startActionRequestHttp(applicationContext, url)
            getNewQuoteButton.isEnabled = false
            textViewQuote.visibility = View.INVISIBLE
            maskedProgressBar.visibility = View.VISIBLE
        }

    }


    private fun getURLFromName(name : String): String {
    return this.baseURL + this.trumpPersonalizedQuoteURL + name
        // return "https://api.whatdoestrumpthink.com/api/v1/quotes/personalized?q=$name"
        //la second solution est aussi correcte
    }




    inner class HttpReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent != null){
                getNewQuoteButton.isEnabled = true
                textViewQuote.visibility = View.VISIBLE
                maskedProgressBar.visibility = View.INVISIBLE
                val message : String? = intent.getStringExtra("responseString")
                animateText()

                if (message != null) {
                    var quoteString: String? = null
                    try {
                        val jsonObject = JSONObject(message)
                        quoteString = jsonObject.getString("message")
                    } catch (e: JSONException) {
                        //displayQuote(resources.getString(R.string.error_message))
                        e.printStackTrace()
                    }
                    if (quoteString != null) {
                        textViewQuote.text = quoteString
                    }
                } else {
                    textViewQuote.text = resources.getString(R.string.Error_message)
                }

            }
        }
    }

    private fun animateText(){
        //creation d'un objet d'animation
        val animation = ObjectAnimator.ofFloat(textViewQuote, "scaleX", 0.0f, 1.0f)
        animation.duration = 1000 //1 seconde (fixé la duration de l'animation)

        //crée les scales d'animations
        val scaleXAnimation = ObjectAnimator.ofFloat(textViewQuote, "scaleX", 0.9f, 1.0f)
        val scaleYAnimation = ObjectAnimator.ofFloat(textViewQuote, "scaleX", 0.9f, 1.0f)

        // créer l'animateur Set
        val animationSet = AnimatorSet()

        //animations de la chaîne
        animationSet.play(scaleXAnimation).with(scaleYAnimation).with(animation)
        //demarrer les animations
        animationSet.start()
    }

}
