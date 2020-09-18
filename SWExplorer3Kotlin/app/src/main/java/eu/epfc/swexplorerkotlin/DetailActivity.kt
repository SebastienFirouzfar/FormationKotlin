package eu.epfc.swexplorerkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // get the intent used to create this Activity and  extract a Planet object from it
        val selectedPlanet : Planet = intent.getSerializableExtra("planetObject") as Planet

        // get a ref to DetailFragment
        val detailFragment = supportFragmentManager.findFragmentById(R.id.detail_fragment) as DetailFragment?

        // send the planet reference to the fragment
        detailFragment?.planet = selectedPlanet
    }
}
