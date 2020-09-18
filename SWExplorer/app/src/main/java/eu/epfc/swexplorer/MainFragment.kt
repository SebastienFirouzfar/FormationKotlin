package eu.epfc.swexplorer


import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE
import android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(), SWPlanetsAdapter.ListItemClickListener {

    private lateinit var planetList : List<Planet>
    private lateinit var recyclerView : RecyclerView
    private var firstStart = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jsonContent = loadJSONFromAsset()
        if (jsonContent != null) {
            planetList = populatePlanetModelsFromJSONString(jsonContent)

            recyclerView = view.findViewById(R.id.recycler_view)
            //recyclerView = activity?.findViewById(R.id.recycler_view) ?: recyclerView
            val planetAdapter = SWPlanetsAdapter(this)
            planetAdapter.planetData = planetList
            recyclerView.adapter = planetAdapter

            val myContent : Context? = context
            if(myContent != null){
                val layoutManager = LinearLayoutManager(myContent)
                // val layoutManager = LinearLayoutManager(context.activity)//fragment
                recyclerView.layoutManager = layoutManager
            }


        }
        else{
            Log.d("MainActivity","Error : no content")
        }
    }



    override fun onStart() {
        super.onStart()

        if (firstStart) {
            // if device is tablet
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // send the first planet to the detail fragment
                val fragmentActivity = activity
                if (planetList.isNotEmpty() && fragmentActivity != null) {

                    val detailFragment =
                        fragmentActivity.supportFragmentManager.findFragmentById(R.id.detail_fragment) as DetailFragment?
                    detailFragment?.planet = planetList[0]//poser la question
                }
            }
        }
        firstStart = false
    }


    private fun populatePlanetModelsFromJSONString(jsonString : String) : List<Planet>{
        val planetList = mutableListOf<Planet>()
        try {
            // obtenir l'élément racine json
            val rootJsonObject = JSONObject(jsonString)
            // obtenir le tableau de l'élément "results" à partir de l'élément racine JSON
            val jsonPlanets = rootJsonObject.getJSONArray("results")
            // pour toutes les planètes énumérées dans l'élément "résultats
            for (i in 0 until jsonPlanets.length()){

                // get the JSON corresponding to the planet
                val jsonPlanet = jsonPlanets.getJSONObject(i)
                val name = jsonPlanet.getString("name")
                val climate = jsonPlanet.getString("climate")
                val terrain = jsonPlanet.getString("terrain")
                val population = jsonPlanet.getLong("population")
                val diameter = jsonPlanet.getInt("diameter")
                val orbitalPeriod = jsonPlanet.getInt("orbital_period")
                val rotationPeriod = jsonPlanet.getInt("rotation_period")

                // create a Planet object and copy all the informations
                //from the JSONObject to the Planet Object
                val newPlanet = Planet(name,climate,terrain,population,diameter,orbitalPeriod,rotationPeriod)
                planetList.add(newPlanet)
            }
        }
        catch (exception : JSONException){
            Log.d("MainActivity", "Error while parsing planet")
        }
        return planetList
    }




    private fun loadJSONFromAsset(): String? {
        val myContent : Context? = context
        if(myContent != null){
            val jsonContent : String
            try {
                val inputStream = myContent.applicationContext.assets.open("planets.json")
                //val inputStream = activity?.applicationContext?.assets?.open("planets.json")
                val reader = BufferedReader(inputStream.reader())
                jsonContent = reader.readText()
                reader.close()
            }
            catch (ex : IOException){
                ex.printStackTrace()
                return null
            }
            return jsonContent
        }
        return null
    }

    override fun onListItemClick(clickedItemIndex : Int){
        val selectedPlanet = planetList[clickedItemIndex]
        val screenSize = resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK

        // si l'appareil est une tablette et l'orientation est le paysage
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE &&
            (screenSize == SCREENLAYOUT_SIZE_LARGE || screenSize == SCREENLAYOUT_SIZE_XLARGE )) {

            // envoyer la planète sélectionnée au fragment de détail
            val fragmentActivity = activity
            if (fragmentActivity != null){
                val detailFragment = fragmentActivity.supportFragmentManager.findFragmentById(R.id.detail_fragment) as DetailFragment?
                detailFragment?.planet = selectedPlanet
            }
        }
        // sinon : l'orientation se fait en portrait ou en courant sur un téléphone
        else{
            // launch detail activity
            //creation de l'itent
            val myContext : Context? = context
            if (myContext != null) {
                val detailIntent = Intent(myContext, DetailActivity::class.java)
                detailIntent.putExtra("planetObject", selectedPlanet)
                startActivity(detailIntent)
            }
        }
    }
}
