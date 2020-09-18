package eu.epfc.helloword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun onClickButton(view: View){
        val mot : EditText = findViewById(R.id.saisirMot)
        val motPer = mot.text.toString()// quand l'user va mettre un mot dans l'appli
        val repPersonne : TextView = findViewById(R.id.reponsePersonne)

        if(motPer.equals("oui")){
            findViewById<LinearLayout>(R.id.root_layout).setBackgroundResource(R.color.green)
            repPersonne.text = "JE SUIS CONTENT" // on affiche le texte
        }

        if(motPer.equals("non")){
            findViewById<LinearLayout>(R.id.root_layout).setBackgroundResource(R.color.red)
            repPersonne.text = "POURQUOI"
        }
    }
}
