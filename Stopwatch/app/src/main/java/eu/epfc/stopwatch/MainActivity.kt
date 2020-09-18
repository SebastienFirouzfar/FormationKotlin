package eu.epfc.stopwatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

   private var seconds : Int = 0
   private var runnig : Boolean = true
   private lateinit var  timeText : TextView
    private lateinit var start : Button
    private lateinit var stop : Button
    private lateinit var reset : Button
    private lateinit var shareButton : Button
    private var wasRunning : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds")
            runnig = savedInstanceState.getBoolean("Running")
        }

        runTimer()

        //button Share
        shareButton = findViewById(R.id.share)
        shareButton.setOnClickListener(){
            val shareIntent = Intent(Intent.ACTION_SEND)
            val messageShare : String = "My time is second $seconds"
            shareIntent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, messageShare)
            startActivity(shareIntent)
        }

        //button Start
        start = findViewById(R.id.buttonStart)
        start.setOnClickListener(){
            this.runnig = true
        }

        //button Stop
        stop = findViewById(R.id.buttonStop)
        stop.setOnClickListener(){
            this.runnig = false
        }

        //button Reset
        reset = findViewById(R.id.buttonReset)
        reset.setOnClickListener(){
            this.seconds  = 0
            this.runnig = false
        }

    }


    override fun onPause(){
        super.onPause()
        wasRunning = runnig
        runnig = false
    }

    /**
     *  override fun onStart(){
    super.onStart()
    if(this.wasRunning){
    runnig = true
    }
    }
     */

    //La fonction star sera appelé automatiquement 
    override fun onResume(){
        super.onResume()
        if(this.wasRunning){
            runnig = true

        }
    }


    override fun onSaveInstanceState(outState : Bundle){
        super.onSaveInstanceState(outState)

        // sauver nos données privées dans le Bund
        outState.putInt("seconds", this.seconds)
        outState.putBoolean("running", this.runnig)

    }


    private fun runTimer(){

        val handler = Handler()
        val runnable = object : Runnable{
            override fun run(){
                timeText = findViewById(R.id.Time)
                val hours : Int = seconds/3600
                val minutes : Int = (seconds%3600)/60
                val secs : Int = seconds%60
                //nous voulons 1 chiffre pour les heures, et 2 chiffres pour les minutes et les secondes
                // -> nous utilisons le format String pour obtenir ce résultat
                val time = String.format(Locale.getDefault(),"%d:%02d: %02d",hours,minutes,secs)
                timeText.text = time

                if(runnig){
                    seconds++
                }
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)

    }

}
