package eu.epfc.epfccommunicator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ReceiveMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_message)


        // sortir le texte de l'intention
        val intent = intent
        val textToDisplay = intent.getStringExtra(Intent.EXTRA_TEXT)

        // régler le texte sur TextView
        val textView : TextView = findViewById(R.id.receive_activity_textview)
        textView.text = textToDisplay

    }
}
