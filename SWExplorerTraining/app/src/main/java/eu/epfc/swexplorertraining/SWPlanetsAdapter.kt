package eu.epfc.swexplorertraining

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SWPlanetsAdapter(listItemClickListener : ListItemClickListener) : RecyclerView.Adapter<SWPlanetsAdapter.PlanetViewHolder>(){

    interface ListItemClickListener {
        fun onListItemClick(clickedItemIndex: Int)
    }

    val listItemClickListener : ListItemClickListener = listItemClickListener

    var planetData : List<Planet>? = null
        set(planetList) {
            field = planetList
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val planetView = layoutInflater.inflate(R.layout.item_planet, parent, false)
        return PlanetViewHolder(planetView)
    }

    override fun getItemCount(): Int {

        val myPlanetData = planetData
        if (myPlanetData != null) {
            return myPlanetData.size
        }
        else
        {
            return 0
        }
    }

    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {

        val myPlanetData = planetData
        if (myPlanetData != null) {
            val planet = myPlanetData[position]
            val itemViewGroup = holder.itemView as ViewGroup
            val planetNameTextView : TextView = itemViewGroup.findViewById(R.id.namePlanets)
            val planetTerrain : TextView = itemViewGroup.findViewById(R.id.terrainPlanet)
            val diameterPlanets : TextView = itemViewGroup.findViewById(R.id.diameterOfPlanet)
            planetNameTextView.text = planet.name

            planetTerrain.text = "Terrain : ${planet.terrain}"
            diameterPlanets.text = "Diameter : ${planet.diameter}"

            val imagePopulations1 : ImageView = itemViewGroup.findViewById(R.id.imagePopulation1)
            val imagePopulations2 : ImageView = itemViewGroup.findViewById(R.id.imagePopulation2)
            val imagePopulations3 : ImageView = itemViewGroup.findViewById(R.id.imagePopulation3)

            val populationPlanet = planet.population
            if(populationPlanet == 0L){

                imagePopulations1.visibility = View.INVISIBLE
                imagePopulations2.visibility = View.INVISIBLE
                imagePopulations3.visibility = View.INVISIBLE
            }else if (populationPlanet <= 100000000L){
                imagePopulations1.visibility = View.VISIBLE
                imagePopulations2.visibility = View.INVISIBLE
                imagePopulations3.visibility = View.INVISIBLE
            }else if (populationPlanet <= 1000000000L){
                imagePopulations1.visibility = View.VISIBLE
                imagePopulations2.visibility = View.VISIBLE
                imagePopulations3.visibility = View.INVISIBLE
            }else{
                imagePopulations1.visibility = View.VISIBLE
                imagePopulations2.visibility = View.VISIBLE
                imagePopulations3.visibility = View.VISIBLE
            }


        }
    }

    inner class PlanetViewHolder : RecyclerView.ViewHolder, View.OnClickListener{

        constructor(itemView: View) : super(itemView){
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val clickedPosition = this.adapterPosition
            this@SWPlanetsAdapter.listItemClickListener.onListItemClick(clickedPosition)
        }
    }
}