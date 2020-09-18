package eu.epfc.pocketmovie


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.epfc.pocketmovie.data.DataRepository
import eu.epfc.pocketmovie.data.PageMovies

/**
 * A simple [Fragment] subclass.
 */
class RecentMovieFragment : Fragment(),MovieAdapter.AdapterListerner, DataRepository.MoviesUIListener {

    private var currentPage:Int=1
    private lateinit var pageMovies : PageMovies
    private val movieAdapter  = MovieAdapter(this)
    private lateinit var recyclerView : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // for ro receive the list of movies 2 frag
        DataRepository.instance.observe(this)
        return inflater.inflate(R.layout.fragment_recent_movie, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

    }

    override fun onStart() {
        super.onStart()
        //ask to reposity a list of movie
        val myContext : Context? = context
        if (myContext != null) {
            DataRepository.instance.getPageMovies(myContext, currentPage)
        }
    }

    override fun onNextPageClick() {
        currentPage +=1
        //changer par le getPagePocket
        val myContext : Context? = context
        if (myContext != null) {
            DataRepository.instance.getPageMovies(myContext, currentPage)
        }
        //findViewById<RecyclerView>(R.id.recyclerView).smoothScrollToPosition(0)
    }

    override fun onPreviousPageClick() {
        if (currentPage > 1) {
            currentPage -=1
            val myContext : Context? = context
            if (myContext != null) {
                DataRepository.instance.getPageMovies(myContext, currentPage)
            }
        }
    }

    override fun  updateUI() {
        //receive a new list of movie et send it to adapter
        val page= DataRepository.instance.pageMovies
        if (page != null){
            pageMovies= page
            movieAdapter.pageMovies = pageMovies
        }
    }




    override fun onListItemClick(clickedItemIndex: Int) {
        val selectedMovieID = pageMovies.movies[clickedItemIndex].id
        val detailIntent = Intent(context, MovieDetailActivity::class.java)

        detailIntent.putExtra("movieID", selectedMovieID)
        startActivity(detailIntent)
    }



}
