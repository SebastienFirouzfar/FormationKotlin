package eu.epfc.pocketmovie

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import eu.epfc.pocketmovie.data.*
import java.lang.Exception

const val  MOVIE_ID = "MOVIE_ID"

class MovieDetailActivity : AppCompatActivity() {


    var movieID:Long=0

    private inner class detailUIListener : DataRepository.DetailUIListener {
        override fun updateUI() {
            val movieDetail = DataRepository.instance.movieDetail
            if (movieDetail!= null) {
                fetchTheMovie(movieDetail)
            }
        }
    }

    private inner class videoUIListener : DataRepository.VideoUIListener {
        override fun updateUI() {
            val videoDetail = DataRepository.instance.videoDetail
            if (videoDetail!= null){
                playVideo(videoDetail)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // il faut sauvegarder le movie ...

        if(savedInstanceState!=null){
            movieID = savedInstanceState.getLong(MOVIE_ID)
        }else{
            movieID = intent.getLongExtra("movieID",0)
        }

        DataRepository.instance.observe(detailUIListener())
        DataRepository.instance.observe(videoUIListener())
    }

    override fun onStart(){
        super.onStart()
        //ask to reposity a spécific movie
        DataRepository.instance.getMovieDetail(this,movieID)
    }

    private fun fetchTheMovie(movieDetail: MovieDetail){
        val titleTextView = findViewById<TextView>(R.id.textTitle)
        val ratingTextView =  findViewById<TextView>(R.id.textRating)
        val dateTextView=findViewById<TextView>(R.id.textDate)
        val overviewTextView = findViewById<TextView>(R.id.textOverview)
        val posterImageView=findViewById<ImageView>(R.id.imagePoster)
        val pocketCheckBox=findViewById<CheckBox>(R.id.checkPocket)
        val videoButton = findViewById<Button>(R.id.buttonVideo)

        titleTextView.text = movieDetail.title
        ratingTextView.text = "rating : ${movieDetail.rating.toString()}"
        dateTextView.text = movieDetail.releaseDate
        overviewTextView.text=movieDetail.overview
        Picasso.get().load(getUrlPoster(movieDetail.poster)).into(posterImageView)
        pocketCheckBox.isChecked = isInPocket(movieDetail.id)


        pocketCheckBox.setOnClickListener({swapMovieInPocket(movieDetail)})
        videoButton.setOnClickListener({askPlayVideo(movieDetail.id)})
    }



    private fun playVideo(videoDetail:VideoDetail){
        val appIntent = Intent(Intent.ACTION_VIEW,Uri.parse("vnd.youtube:${videoDetail.key}"))
        val webIntent = Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v=${videoDetail.key}"))
        try {
            startActivity(appIntent)
        } catch (e:Exception){
            startActivity(webIntent)
        }
    }

    //fonction appelant Youtube
    private fun askPlayVideo(movieID: Long){
        DataRepository.instance.getVideoDetail(this,movieID)
    }


    //fonction question au DAO si le film est dans le Pocket
    private fun isInPocket(movieID:Long):Boolean{

        val pocketDAO = MovieDataBase.getInstance(this).pocketDAO()
        val movie = pocketDAO.getMovieByID(movieID)
        return movie!=null


        return false
    }

    private fun swapMovieInPocket(movie:MovieDetail){
        if (isInPocket(movie.id)){ // le film y est on retire
            removeMovieInPocket(movie.id)
        }else{ //sinon l'inverse: on l'y met
            addMovieInPocket(movie)
        }
    }


    /**
     * Si le film n'est pas dans la page pocket on ajoute ceci
     */
    private fun addMovieInPocket(detail: MovieDetail){
        val pocketDAO = MovieDataBase.getInstance(this).pocketDAO()
        val movie = Movie(detail.id,detail.title,detail.rating,detail.poster)
        pocketDAO.addMovieInPocket(movie)

    }

    /**
     * On supprime le film qui se trouve dans la page pocket
     */
    private fun removeMovieInPocket(movieID: Long){
        val pocketDAO = MovieDataBase.getInstance(this).pocketDAO()
        pocketDAO.removeMovieInPocket(movieID)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // save the current movieID

        outState.putLong(MOVIE_ID,movieID)

    }

}
