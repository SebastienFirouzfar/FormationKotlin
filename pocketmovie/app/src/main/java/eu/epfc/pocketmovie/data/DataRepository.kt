package eu.epfc.pocketmovie.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.Toast
import eu.epfc.pocketmovie.HttpRequestService
import eu.epfc.pocketmovie.getApiKey
import org.json.JSONException
import org.json.JSONObject
import java.lang.ref.WeakReference




class DataRepository {
    private constructor() // don't create this object by code

    companion object{
        const val HTTP_RECEIVER_PAGE_COMPLETE   = "HTTP_RECEIVER_PAGE_COMPLETE"
        const val HTTP_RECEIVER_PAGE_ERROR      = "HTTP_RECEIVER_PAGE_ERROR"
        const val HTTP_RECEIVER_DETAIL_COMPLETE = "HTTP_RECEIVER_DETAIL_COMPLETE"
        const val HTTP_RECEIVER_DETAIL_ERROR    = "HTTP_RECEIVER_DETAIL_ERROR"
        const val HTTP_RECEIVER_VIDEO_COMPLETE = "HTTP_RECEIVER_VIDEO_COMPLETE"
        const val HTTP_RECEIVER_VIDEO_ERROR    = "HTTP_RECEIVER_VIDEO_ERROR"


        fun getPageUrlString(pageID:Int): String="https://api.themoviedb.org/3/movie/popular?api_key=${getApiKey()}&language=${getLanguage()}&page=${pageID}"
        fun getMovieUrlString(movieID:Long): String="https://api.themoviedb.org/3/movie/${movieID}?api_key=${getApiKey()}&language=${getLanguage()}"
        fun getVideoUrlString(movieID:Long): String="https://api.themoviedb.org/3/movie/${movieID}/videos?api_key=${getApiKey()}&language=${getLanguage()}"

        private fun getLanguage():String{
            return "en-US"
        }
        val instance = DataRepository()
    }



    interface UIListener {        fun updateUI()    }
    interface MoviesUIListener :UIListener //for http request "pages" from TMDB api
    interface DetailUIListener :UIListener //for http request "detail" from TMDB api
    interface PocketUIListener :UIListener //for DAO request (Pocket)
    interface VideoUIListener    :UIListener //for Video request

    private var moviesUIListener: WeakReference<MoviesUIListener>? = null //store 2/2 the listener for  page from TMDB api
    private var pocketUIListener: WeakReference<PocketUIListener>? = null //store 2/2 the listener for  page from Pocket DAO
    private var detailUIListener: WeakReference<DetailUIListener>? = null //store 2/2 the listener for movie's detail
    private var videoUIListener : WeakReference<VideoUIListener>? = null  //store 2/2 the listener for video

    /**
     *  Publics fields
     *
     *
     */
    var pageMovies  : PageMovies?  = null //store the current page from TMDB api
    var pagePocket  : PageMovies?  = null //store the current page from Pocket
    var movieDetail : MovieDetail? = null //store the current movie's detail
    var videoDetail : VideoDetail? = null //store the current video

    /**
     *   Publiques Methods
     */
    fun observe(moviesUIListener: MoviesUIListener) { this.moviesUIListener = WeakReference(moviesUIListener) } //store 1/2 the listener for  page from TMDB api
    fun observe(pocketUIListener: PocketUIListener) { this.pocketUIListener = WeakReference(pocketUIListener) } //store 1/2 the listener for  page from Pocket DAO
    fun observe(detailUIListener: DetailUIListener) { this.detailUIListener = WeakReference(detailUIListener) } //store 1/2 the listener for movie's detail
    fun observe(videoUIListener : VideoUIListener)  { this.videoUIListener  = WeakReference(videoUIListener)  } //store 1/2 the listener for movie's detail



    private var httpReceiverInitialized = false


    fun updateUIListener(uiListener: UIListener?){
        uiListener?.updateUI()
    }



    fun getPageMovies(context: Context, pageID:Int) {

        if (!httpReceiverInitialized) {
            initHttpReceiverWithContext(context)
            httpReceiverInitialized = true
        }

        HttpRequestService.startActionRequestHttp(
            context,
            getPageUrlString(pageID),
            HTTP_RECEIVER_PAGE_COMPLETE,
            HTTP_RECEIVER_PAGE_ERROR)
    }

    fun getMovieDetail(context: Context, movieID:Long) {

        if (!httpReceiverInitialized) {
            initHttpReceiverWithContext(context)
            httpReceiverInitialized = true
        }
        HttpRequestService.startActionRequestHttp(
            context,
            getMovieUrlString(movieID),
            HTTP_RECEIVER_DETAIL_COMPLETE,
            HTTP_RECEIVER_DETAIL_ERROR
        )
    }

    fun getPagePocket(context: Context, pageID:Int){
        val pocketDAO = MovieDataBase.getInstance(context).pocketDAO()
        pagePocket = PageMovies(pageID, pocketDAO.getMoviesInPocketByPage(pageID),false)

        updateUIListener(pocketUIListener?.get())
    }


    fun getVideoDetail(context: Context, movieID:Long){
        if (!httpReceiverInitialized) {
            initHttpReceiverWithContext(context)
            httpReceiverInitialized = true
        }
        HttpRequestService.startActionRequestHttp(
            context,
            getVideoUrlString(movieID),
            HTTP_RECEIVER_VIDEO_COMPLETE,
            HTTP_RECEIVER_VIDEO_ERROR
        )


    }



/*
    fun isInThePocket(context: Context, movieID: Long):Boolean{
        val pocketDAO = MovieDataBase.getInstance(context).pocketDAO()
        val movie = pocketDAO.getMovieByID(movieID)
        return movie!=null
    }

*/

    inner class HttpPageReceiver:BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            if (intent.action == HTTP_RECEIVER_PAGE_COMPLETE){
                // extract JSON response from the intent
                val response = intent.getStringExtra(HttpRequestService.HTTP_RESPONSE_STRING)
                val newPageMovie = parsePageMoviesResponse(response)
                if(newPageMovie!=null){
                    pageMovies = newPageMovie
                    updateUIListener(moviesUIListener?.get())
                }else{
                    Toast.makeText(context,"Erreur : no parsing page",Toast.LENGTH_LONG).show()
                }

            } else if (intent.action == HTTP_RECEIVER_PAGE_ERROR) {
                Toast.makeText(context,"Erreur : $HTTP_RECEIVER_PAGE_ERROR",Toast.LENGTH_LONG).show()
            }
        }
    }

    inner class HttpDetailReceiver:BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            if (intent.action == HTTP_RECEIVER_DETAIL_COMPLETE){
                // extract JSON response from the intent
                val response = intent.getStringExtra(HttpRequestService.HTTP_RESPONSE_STRING)
                val newMovieDetail = parseDetailMoviesResponse(response)

                if (newMovieDetail !=null){

                    movieDetail = newMovieDetail
                    updateUIListener(detailUIListener?.get())

                }else{
                    Toast.makeText(context,"Erreur : no parsing detail",Toast.LENGTH_LONG).show()
                }

            } else if (intent.action == HTTP_RECEIVER_DETAIL_ERROR) {
                Toast.makeText(context,"Erreur : $HTTP_RECEIVER_DETAIL_ERROR",Toast.LENGTH_LONG).show()
            }
        }
    }

    inner class HttpVideoReceiver:BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            if (intent.action == HTTP_RECEIVER_VIDEO_COMPLETE){
                // extract JSON response from the intent
                val response = intent.getStringExtra(HttpRequestService.HTTP_RESPONSE_STRING)
                val newVideo = parseVideoResponse(response)

                if (newVideo !=null){

                    videoDetail = newVideo
                    updateUIListener(detailUIListener?.get())

                }else{
                    Toast.makeText(context,"Erreur : no parsing video JSON",Toast.LENGTH_LONG).show()
                }

            } else if (intent.action == HTTP_RECEIVER_VIDEO_ERROR) {
                Toast.makeText(context,"Erreur : $HTTP_RECEIVER_VIDEO_ERROR",Toast.LENGTH_LONG).show()
            }

            updateUIListener(videoUIListener?.get())
        }
    }



    /**
     *  L'INITIALISATION  se fait une unique fois dans le déroulement de l'application ...
     *  d'où une initialisation commune
     */

    private fun initHttpReceiverWithContext(context: Context) {
        // register the broadcast receiver to intent with action "httpRequestComplete" and "httpRequestError"

        fun initSpcecific(receiver: BroadcastReceiver,actionComplete:String, actionError:String){
            val intentFilter = IntentFilter()
            intentFilter.addAction(actionComplete)
            intentFilter.addAction(actionError)
            context.applicationContext.registerReceiver(receiver, intentFilter)
        }

        initSpcecific(this.HttpPageReceiver(),
            HTTP_RECEIVER_PAGE_COMPLETE,
            HTTP_RECEIVER_PAGE_ERROR)

        initSpcecific(this.HttpDetailReceiver(),
            HTTP_RECEIVER_DETAIL_COMPLETE,
            HTTP_RECEIVER_DETAIL_ERROR)

        initSpcecific(this.HttpVideoReceiver(),
            HTTP_RECEIVER_VIDEO_COMPLETE,
            HTTP_RECEIVER_VIDEO_ERROR)
    }


    private fun parsePageMoviesResponse(jsonString: String): PageMovies {

        var movies = ArrayList<Movie>()
        var page = 0

        try {
            val jsonObject = JSONObject(jsonString)
            //on récupère le numéro de page
             page = jsonObject.getString("page").toInt()

            val jsonMovies = jsonObject.getJSONArray("results")
            for (i in 0 until jsonMovies.length()) {
                val jsonMovie = jsonMovies.getJSONObject(i)

                val id = jsonMovie.getString("id").toLong()
                val title = jsonMovie.getString("title")
                val rate = jsonMovie.getString("vote_average")
                val poster = jsonMovie.getString("poster_path")
                movies.add(Movie(id,title,rate,poster))
            }
        } catch (e: JSONException) {
            Log.e("parsePageResponse", "can't parse json string correctly")
            e.printStackTrace()
        }

        return PageMovies(page,movies,true)
    }


    private fun parseDetailMoviesResponse(jsonString: String): MovieDetail {

        lateinit var movieDetail : MovieDetail
        //var movieData : MovieDetail?=null

        try {
            val jsonMovie = JSONObject(jsonString)
            val id = jsonMovie.getString("id").toLong()
            val title = jsonMovie.getString("title")
            val rate = jsonMovie.getString("vote_average")
            val poster = jsonMovie.getString("poster_path")
            val overview=jsonMovie.getString("overview")
            val releaseDate=jsonMovie.getString("release_date")

            movieDetail = MovieDetail(id,title,rate,poster,overview,releaseDate)
        }
        catch (e: JSONException) {
            Log.e("MovieRepository", "can't parse json string correctly")
            e.printStackTrace()
        }

        return movieDetail
    }

    private fun parseVideoResponse(jsonString: String): VideoDetail? {

        var video: VideoDetail? = null


        try {
            val jsonObject = JSONObject(jsonString)
            val jsonVideos = jsonObject.getJSONArray("results")

            if (jsonVideos.length() > 0) {
                val jsonVideo = jsonVideos.getJSONObject(0)
                val id = jsonVideo.getString("id")
                val key = jsonVideo.getString("key")
                val site = jsonVideo.getString("site")
                val type = jsonVideo.getString("type")
                video = VideoDetail(id, key, site, type)
            }
        } catch (e: JSONException) {
            Log.e("JFL: parseVideoResponse", "can't parse json string correctly")
            e.printStackTrace()
        }

        return video

    }

}


