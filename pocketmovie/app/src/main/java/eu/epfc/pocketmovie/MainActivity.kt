package eu.epfc.pocketmovie
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import eu.epfc.pocketmovie.data.DataRepository
import eu.epfc.pocketmovie.data.PageMovies
import kotlinx.android.synthetic.main.activity_main.*


fun getApiKey() = "ea2dcee690e0af8bb04f37aa35b75075"
fun getUrlPoster(poster: String):String = "https://image.tmdb.org/t/p/w500${poster}"


class MainActivity : AppCompatActivity(){

    //private var currentPage:Int=1
    //private val movieAdapter  = MovieAdapter(this)
    //private lateinit var pageMovies : PageMovies
    private lateinit var buttonInformation : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //configure the recyclerView 2 frag
       // val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //recyclerView.adapter = movieAdapter
        //recyclerView.layoutManager = LinearLayoutManager(this)

        // for ro receive the list of movies 2 frag
       // DataRepository.instance.observe(this)

        // fragment Movie VOIR DANS LE PDF LABO 3EME  LABO10 reste dans cette classe
        val adapter = MyFragmentPager(supportFragmentManager)
        adapter.addFragment(RecentMovieFragment(), "RECENTS MOVIES")
        adapter.addFragment(PocketFragment(), "POCKET")
        //view_pager.adapter = adapter
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        viewPager.adapter = adapter
        //activity_mainTab.setupWithViewPager(view_pager)
        val tab = findViewById<TabLayout>(R.id.tab_layout)
        tab.setupWithViewPager(viewPager)

    }

    //les 4 methodes doivent aller dans le fragment
    override fun onStart() {
        super.onStart()
        //ask to reposity a list of movie
        //DataRepository.instance.getPageMovies(this,currentPage)
    }

    fun buttonInformationPage(view: View){
        buttonInformation = findViewById(R.id.imageButton)
        val intent = Intent(this, informationMovie::class.java)
        startActivity(intent)
    }


    /**
     * La navigation avec des films recents et les pockets
     */
    class MyFragmentPager(manager: FragmentManager): FragmentPagerAdapter(manager){
        val listFragment : MutableList<Fragment> = ArrayList()
        val titleString : MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return listFragment[position]
        }

        override fun getCount(): Int {
            return titleString.size
        }

        fun addFragment(fragment: Fragment, title : String){
            listFragment.add(fragment)
            titleString.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleString[position]
        }

    }



}



