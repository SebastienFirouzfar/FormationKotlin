package eu.epfc.swexplorerkotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException


class MainActivity : AppCompatActivity(), SWPlanetsAdapter.ListItemClickListener {


    private lateinit  var planetList : List<Planet>
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonContent = loadJSONFromAsset()

        if (jsonContent != null) {
            planetList = populatePlanetModelsFromJSONString(jsonContent)

            recyclerView = findViewById(R.id.recycler_view)
            val planetAdapter = SWPlanetsAdapter(this)
            planetAdapter.planetData = planetList
            recyclerView.adapter = planetAdapter

            val context : Context = this
            val layoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = layoutManager

            val planetItemDecoration = PlanetItemDecoration(32, 16)
            recyclerView.addItemDecoration(planetItemDecoration)

        }
        else{
            Log.d("MainActivity","Error : no content")
        }

    }

    private fun loadJSONFromAsset(): String? {

        val jsonContent: String
        try {
            val inputStream = applicationContext.assets.open("planets.json")
            val reader = BufferedReader(inputStream.reader())
            jsonContent = reader.readText()
            reader.close()
        }
        catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return jsonContent
    }

    /**
     * This method create Planet objects from a json String and return them in a List<Planet>
     * @param jsonString the json String containing planet informations
     */
    private fun populatePlanetModelsFromJSONString(jsonString : String) : List<Planet>{
        val planetList = mutableListOf<Planet>()
        try {
            // get the root json element
            val rootJsonObject = JSONObject(jsonString)
            // get the "results" element array from the root JSON element
            val jsonPlanets = rootJsonObject.getJSONArray("results")
            // for all the planets listed in the “results” element
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

    override fun onListItemClick(clickedItemIndex: Int) {
        val selectedPlanet = planetList[clickedItemIndex]

        val detailIntent = Intent(this, DetailActivity::class.java)
        detailIntent.putExtra("planetObject", selectedPlanet)
        startActivity(detailIntent)
    }
}
