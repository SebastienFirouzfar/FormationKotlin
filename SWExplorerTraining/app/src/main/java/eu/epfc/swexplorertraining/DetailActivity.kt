package eu.epfc.swexplorertraining

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val selectedPlanet : Planet = intent.getSerializableExtra("planetObject") as Planet


        // set orbital period TextView
        val orbitalPeriodTextView : TextView = findViewById(R.id.orbitalPeriod)
        orbitalPeriodTextView.text = selectedPlanet.orbitalPeriod.toString()

        // set rotation period TextView
        val rotationPeriodTextView : TextView = findViewById(R.id.rotationPeriod)
        rotationPeriodTextView.text = selectedPlanet.rotationPeriod.toString()

        // set diameter TextView
        val diameterTextView : TextView = findViewById(R.id.diameterPlanet)
        diameterTextView.text = selectedPlanet.diameter.toString()

        // set climate TextView
        val climateTextView : TextView = findViewById(R.id.climatePlanet)
        climateTextView.text = selectedPlanet.climate

        // set planet name TextView
        val planetTextView : TextView = findViewById(R.id.name_planet)
        planetTextView.text = selectedPlanet.name


        // the file names are always lower case
        val planetImageName = selectedPlanet.name.toLowerCase()

        // get the resource ID of the image file from its name
        var resourceId = resources.getIdentifier(planetImageName, "drawable", packageName)

        if (resourceId == 0) {     // get the resource from the generic_planet image
            resourceId = resources.getIdentifier("generic_planet", "drawable", packageName)
        }

        val imageView : ImageView = findViewById(R.id.image_planet)
        imageView.setImageResource(resourceId)

    }
}
