package eu.epfc.epfccommunicatortraining

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ReceiveMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_message)

        //recevoir le text donnée de l'itent
        val displayText = intent.getStringExtra(Intent.EXTRA_TEXT)

        val textView : TextView = findViewById(R.id.receiveMessage)
        textView.text = displayText
    }
}
