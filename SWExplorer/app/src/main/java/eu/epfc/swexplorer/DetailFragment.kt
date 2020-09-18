package eu.epfc.swexplorer


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 */
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

        //affichage
        val myPlanet = planet
        val myView = view

        if(myView != null && myPlanet != null){
            val periodRotation : TextView = myView.findViewById(R.id.numberRotation)
            periodRotation.text = myPlanet.rotationPeriod.toString()

            val orbitalPeriod : TextView = myView.findViewById(R.id.numberOrbital)
            orbitalPeriod.text = myPlanet.orbitalPeriod.toString()

            val diameterPeriod : TextView = myView.findViewById(R.id.numberDiameter)
            diameterPeriod.text = myPlanet.diameter.toString()

            val climatePeriod : TextView = myView.findViewById(R.id.climate_detail)
            climatePeriod.text = myPlanet.climate

            val namePlanet = myPlanet.name.toLowerCase()

            var resourceId = resources.getIdentifier(namePlanet, "drawable", activity?.packageName)

            if(resourceId == 0){
                resourceId = resources.getIdentifier("generic_planet", "drawable", activity?.packageName)
            }
            val imageView : ImageView = myView.findViewById(R.id.alderaanPhoto)
            imageView.setImageResource(resourceId)

        }
    }



}
