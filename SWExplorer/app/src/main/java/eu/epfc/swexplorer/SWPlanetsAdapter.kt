package eu.epfc.swexplorer

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView

//Qui va definir le comportement le nombre d'item
class SWPlanetsAdapter(listItemClickListener : ListItemClickListener) : RecyclerView.Adapter<SWPlanetsAdapter.PlanetViewHolder>(){

    var  planetData  : List<Planet>? = null
    val listItemClickListener : ListItemClickListener = listItemClickListener
    interface ListItemClickListener{
        fun onListItemClick(clickedItemIndex : Int){

        }
    }

    /**
     * exo 7
     * Constructeur primaire avec itemView : vies dans les parametres
     */
                                    //classe                interface
    inner class PlanetViewHolder : RecyclerView.ViewHolder, View.OnClickListener{

        constructor(itemView: View) : super(itemView){
            itemView.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
           // mainActivity.itemClicked(this.adapterPosition)
            val clickedPosition = this.adapterPosition
            this@SWPlanetsAdapter.listItemClickListener.onListItemClick(clickedPosition)
        }

    }

    var planetDatas : List<Planet>? = null
        set(planetList) {
            field = planetList
            notifyDataSetChanged()
        }


    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        val planetList = planetData
        if (planetList != null){
            val planet = planetList[position]
            val itemViewGroup = holder.itemView as ViewGroup
            val textView : TextView = itemViewGroup.findViewById(R.id.text_planet_name)
            val planetDiameterViewGroup : TextView = itemViewGroup.findViewById(R.id.textViewDiameter)
            val planetTerrainViewGroup : TextView = itemViewGroup.findViewById(R.id.textViewTerrain)
            textView.text = planet.name
            //on recupere les données de la classe Planet
            planetDiameterViewGroup.text =  "Diameter: ${planet.diameter}"
            planetTerrainViewGroup.text =  "Terrain: ${planet.terrain}"


            //On récupere les images
            val populationImageView1 : ImageView = itemViewGroup.findViewById(R.id.image_Population1)
            val populationImageView2 : ImageView = itemViewGroup.findViewById(R.id.image_Population2)
            val populationImageView3 : ImageView = itemViewGroup.findViewById(R.id.image_Population3)


            if(planet.population == 0L){
                //itemViewGroup.setBackgroundResource(R.color.zeroHabitant) exo 7
                populationImageView1.visibility = View.INVISIBLE
                populationImageView2.visibility = View.INVISIBLE
                populationImageView3.visibility = View.INVISIBLE

            }else if(planet.population <= 100000000L){
                //itemViewGroup.setBackgroundResource(R.color.sansHabitant) exo 7
                populationImageView1.visibility = View.VISIBLE
                populationImageView2.visibility = View.INVISIBLE
                populationImageView3.visibility = View.INVISIBLE

            }else if (planet.population <= 1000000000L){
                //itemViewGroup.setBackgroundResource(R.color.unMillairdHabitant) exo 7
                populationImageView1.visibility = View.VISIBLE
                populationImageView2.visibility = View.VISIBLE
                populationImageView3.visibility = View.INVISIBLE

            }else{
                //itemViewGroup.setBackgroundResource(R.color.plusUnMilliar) exo 7
                populationImageView1.visibility = View.VISIBLE
                populationImageView2.visibility = View.VISIBLE
                populationImageView3.visibility = View.VISIBLE
            }
        }

    }

    override fun getItemCount(): Int {
        val myPlanetData = planetData
        if(myPlanetData != null){
            return myPlanetData.size
        }else{
            return 0
        }
        //ou faire return planet?.size ?: 0

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        // obtenir un layoutInflater à partir du contexte attaché à la vue parentale
        val layoutInflater = LayoutInflater.from(parent.context)

        // gonfler le layout item_planet dans une vue
        val planetView = layoutInflater.inflate(R.layout.item_planet, parent, false)

        return PlanetViewHolder(planetView)
    }

}