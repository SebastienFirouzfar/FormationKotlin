package eu.epfc.swexplorerkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView



class SWPlanetsAdapter(listItemClickListener : ListItemClickListener) : RecyclerView.Adapter<SWPlanetsAdapter.PlanetViewHolder>(){

    /**
     * Interface definition
     */
    interface ListItemClickListener {
        fun onListItemClick(clickedItemIndex: Int)
    }

    // listener that will be called when an item is clicked
    val listItemClickListener : ListItemClickListener = listItemClickListener

    var planetData : List<Planet>? = null
        set(planetList) {
            field = planetList
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        // get a layoutInflater from the context attached to the parent view
        val layoutInflater = LayoutInflater.from(parent.context)

        // inflate the layout item_planet in a view
        val planetView = layoutInflater.inflate(R.layout.item_planet, parent, false)

        // create a new ViewHolder with the view planetView
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

            // get the corresponding planet data (based on the parameter position)
            val planet = myPlanetData[position]

            // get the TextView of the item
            val itemViewGroup = holder.itemView as ViewGroup
            val planetNameTextView : TextView = itemViewGroup.findViewById(R.id.text_planet_name)
            val planetDiameterTextView : TextView = itemViewGroup.findViewById(R.id.text_item_diameter)
            val planetTerrainTextView : TextView = itemViewGroup.findViewById(R.id.text_item_terrain)
            planetNameTextView.text = planet.name
            planetDiameterTextView.text = "Diameter: ${planet.diameter}"
            planetTerrainTextView.text = "Terrain: ${planet.terrain}"

            // get imageViews for population
            val populationImageView1 : ImageView = itemViewGroup.findViewById(R.id.image_population1)
            val populationImageView2 : ImageView = itemViewGroup.findViewById(R.id.image_population2)
            val populationImageView3 : ImageView = itemViewGroup.findViewById(R.id.image_population3)

            // set the color of the background depending on the planet population
            if (planet.population == 0L) {
                populationImageView1.visibility = View.INVISIBLE
                populationImageView2.visibility = View.INVISIBLE
                populationImageView3.visibility = View.INVISIBLE
            }
            else if (planet.population <= 100000000L) {
                populationImageView1.visibility = View.VISIBLE
                populationImageView2.visibility = View.INVISIBLE
                populationImageView3.visibility = View.INVISIBLE

            }
            else if (planet.population <= 1000000000L) {
                populationImageView1.visibility = View.VISIBLE
                populationImageView2.visibility = View.VISIBLE
                populationImageView3.visibility = View.INVISIBLE
            }
            else {
                populationImageView1.visibility = View.VISIBLE
                populationImageView2.visibility = View.VISIBLE
                populationImageView3.visibility = View.VISIBLE
            }
        }
    }

    inner class PlanetViewHolder : RecyclerView.ViewHolder, View.OnClickListener{

        constructor(itemView: View) : super(itemView){
            // define the object to call when the view will be clicked
            itemView.setOnClickListener(this)
        }

        /**
         * Callback method called when the view is clicked
         */
        override fun onClick(view: View?) {

            val clickedPosition = this.adapterPosition
            this@SWPlanetsAdapter.listItemClickListener.onListItemClick(clickedPosition)
        }

    }
}