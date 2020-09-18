package eu.epfc.epfccommunicator

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import java.io.ByteArrayOutputStream
import java.net.URI

class MainActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 15154 // le code pour trouver l'activite Pour le numero on met ce qu'on veut
    private var imageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onSendButtonClicked(view : View){ // ceci est un callback
        val messageEditText : EditText = findViewById(R.id.edit_text_message)
        val editTextString = messageEditText.text.toString()

        //Ceci va afficher une nouvelle classe une fois qu'on a cliquer dans send
        val intent = Intent(this, ReceiveMessageActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, editTextString)

        //démarrer l'activité ReceiveMessageActivity
        startActivity(intent)
    }

    //Afficher le mot pour envoyer un message par mail
    fun buttonShare(view: View){
       val intentShare = Intent(Intent.ACTION_SEND)
        intentShare.type = "text/plain"

        val buttonShareMessage : EditText = findViewById(R.id.edit_text_message)
        val messageShare : String = buttonShareMessage.text.toString()

        val intent = Intent(this, ReceiveMessageActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, messageShare)

        //démarrer l'activité ReceiveMessageActivity
        startActivity(intentShare)

    }

    //mettre l'adresse dans maps
    fun buttonSeach(view: View){
        val messageSearch :EditText = findViewById(R.id.messageSearch)
        val searchToMaps = messageSearch.text

        val intenMaps = Intent(Intent.ACTION_VIEW)
        val geoURI = Uri.parse("geo:0,0?q=$searchToMaps")
        intenMaps.data = geoURI
        startActivity(intenMaps)
    }

    fun onTakePictureButtonClicked(view: View){
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(takePicture.resolveActivity(packageManager) != null){
                startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // si c'est le résultat de l'activité que nous avons lancée plus tôt
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //
            if (data != null) {
                // extraire l'image de l'intention
                val extras = data.extras
                if (extras != null) {
                    imageBitmap = extras.get("data") as Bitmap

                    // afficher l'image dans l'ImageView
                    val cameraThumbnailImageView: ImageView = findViewById(R.id.cameraThumbnailImageView)
                    cameraThumbnailImageView.setImageBitmap(imageBitmap)
                }
            }
        }
    }

    fun onImageViewClicked(view: View) {

        val myImageBitmap : Bitmap? = imageBitmap
        if (myImageBitmap != null) {

            //Convertir Bitmap en tableau d'octets
            val stream = ByteArrayOutputStream() //pour compresser la taille de l'image
            myImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()

            // créer une Intention explicite avec la classe ReceiveMessageActivity
            val intent = Intent(this, FullImageActivity::class.java)
            // ajouter l'image dans l'intent
            intent.putExtra("image", byteArray)
            // start the activity FullImageActivity
            startActivity(intent)
        }
    }





}
