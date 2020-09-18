package eu.epfc.trumpfacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class StartActivity : AppCompatActivity() {

    private lateinit var goButton : Button
    private lateinit var nameEditText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)



        goButton  = findViewById(R.id.buttonGo)
        nameEditText  = findViewById(R.id.textEntrerName)
        //Button GO
        goButton.setOnClickListener {
            val intent = Intent(this, DisplayQuoteActivity::class.java)
            val message = nameEditText.getText().toString()
            //ajout des données
            intent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(intent)
        }

    }



}
