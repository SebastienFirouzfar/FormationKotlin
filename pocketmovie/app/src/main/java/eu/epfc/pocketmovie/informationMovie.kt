package eu.epfc.pocketmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.content.ActivityNotFoundException
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log


class informationMovie : AppCompatActivity() {

    private lateinit var buttonMessage : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_movie)
    }

    fun buttonSendMessage(view : View){
        buttonMessage = findViewById(R.id.sendMessage)
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.type = "text/plain"
        val mailto = "mailto:quhouben@epfc.eu" +
                "?cc=" + "" +
                "&subject=" + emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pocket movie") +
                "&body=" + Uri.encode("Feedback from PocketMovie version 1 : ")
        emailIntent.data = Uri.parse(mailto)
        try {
            startActivity(emailIntent)
        } catch (e: ActivityNotFoundException) {

        }
    }
}
