package eu.epfc.swexplorerkotlin


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class DetailFragment : Fragment() {

    var planet : Planet? = null
    set(value) {
        field = value
        reloadUI()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onStart() {
        super.onStart()
        reloadUI()
    }

    private fun reloadUI(){
        val fragmentView = view
        val selectedPlanet = this.planet
        if (fragmentView != null && selectedPlanet != null) {
            // set orbital period TextView
            val orbitalPeriodTextView: TextView = fragmentView.findViewById(R.id.text_orbital_period_value)
            orbitalPeriodTextView.text = selectedPlanet.orbitalPeriod.toString()

            // set rotation period TextView
            val rotationPeriodTextView: TextView = fragmentView.findViewById(R.id.text_rotation_period_value)
            rotationPeriodTextView.text = selectedPlanet.rotationPeriod.toString()

            // set diameter TextView
            val diameterTextView: TextView = fragmentView.findViewById(R.id.text_diameter_value)
            diameterTextView.text = selectedPlanet.diameter.toString()

            // set climate TextView
            val climateTextView: TextView = fragmentView.findViewById(R.id.text_climate_value)
            climateTextView.text = selectedPlanet.climate

            // set planet name TextView
            val planetTextView: TextView = fragmentView.findViewById(R.id.text_planet_value)
            planetTextView.text = selectedPlanet.name

            // the file names are always lower case
            val planetImageName = selectedPlanet.name.toLowerCase()

            // get the resource ID of the image file from its name
            var resourceId = resources.getIdentifier(planetImageName, "drawable", activity?.packageName)

            // if there is no image for that planet
            if (resourceId == 0) {
                // get the resource from the generic_planet image
                resourceId = resources.getIdentifier("generic_planet", "drawable", activity?.packageName)
            }
            val imageView: ImageView = fragmentView.findViewById(R.id.image_planet)
            imageView.setImageResource(resourceId)
        }
    }
}
