package eu.epfc.epfccommunicator

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class FullImageActivity : AppCompatActivity() {

    // cette variable spécifie si l'imageView est en mode CENTER_CROP ou en mode FIT_CENTER
    private var isInFillMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)

        val intent = intent

        if(intent.hasExtra("image")){// hasExtra lui dit retourne vrai si une valeur supplémentaire est associée au prénom.

            //obtenir un tableau d'octets à partir de l'intention
            val byteArray = intent.getByteArrayExtra("image")

            //décoder le tableau d'octets en Bitmap
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

            // définir l'image bitmap sur l'imageView
            val imageView : ImageView = findViewById(R.id.full_imageview)
            imageView.setImageBitmap(bitmap)
        }
    }

    fun onImageViewClicked(view: View) {
        isInFillMode = !isInFillMode
        val imageView = view as ImageView
        if (isInFillMode) {
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        } else {
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        }
    }


}
