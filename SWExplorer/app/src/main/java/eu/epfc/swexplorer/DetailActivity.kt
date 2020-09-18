package eu.epfc.swexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val selectedPlanet : Planet = intent.getSerializableExtra("planetObject")as Planet

        // obtenir une référence à DetailFragment
        val detailFragment = supportFragmentManager.findFragmentById(R.id.detail_fragment) as DetailFragment

        // envoyer la référence de la planète au fragment
        detailFragment?.planet = selectedPlanet

    }
}
