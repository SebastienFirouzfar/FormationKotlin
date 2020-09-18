package eu.epfc.epfccommunicatortraining

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 15653
    private var imageBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onSendButtonClicked(view: View){
        val messageEditText : EditText = findViewById(R.id.edit_text_message)
        val editTextString = messageEditText.text.toString()

        //creation d'un itent
        val intent = Intent(this, ReceiveMessageActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, editTextString)
        startActivity(intent)
    }

    fun ButtonShare(view: View){
        val messageEditText : EditText = findViewById(R.id.edit_text_message)
        val editTextString = messageEditText.text.toString()
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, editTextString)
        startActivity(intent)
    }

    fun buttonSearch(view: View){
        val SearchEditText : EditText = findViewById(R.id.messageSearch)
        val adressString = SearchEditText.text.toString()
        val intentMap = Intent(Intent.ACTION_VIEW)
        val adressUri = Uri.parse("geo:0,0?q=$adressString")
        intentMap.data = adressUri
        startActivity(intentMap)
    }

    fun onTakePictureButtonClicked(view: View){
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(takePicture != null){
            startActivityForResult(takePicture, this.REQUEST_IMAGE_CAPTURE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == this.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            if(data != null){
                val extras = data.extras

                if(extras!=null){
                    imageBitmap = extras.get("data") as Bitmap
                    val cameraView : ImageView = findViewById(R.id.photoAndroid)
                    cameraView.setImageBitmap(imageBitmap)
                }
            }

        }
    }


}
