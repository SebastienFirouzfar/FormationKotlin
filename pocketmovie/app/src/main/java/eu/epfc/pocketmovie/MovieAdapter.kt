package eu.epfc.pocketmovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import eu.epfc.pocketmovie.data.PageMovies


class MovieAdapter(adapterListerner: AdapterListerner) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){
    /**
     * This interface is the message (for activity or else) for ask the display of a movie detail
     */
    interface AdapterListerner:ListItemClickListener,NavigationClickListener
    /**
     * This interface is the message (for activity or else) for ask the display of a movie detail
     */
    interface ListItemClickListener {
        fun onListItemClick(clickedItemIndex: Int)
    }
    /**
     * This interface is the message (for activity or else) for ask new page (Next or Previous)
     */
    interface NavigationClickListener {
        fun onNextPageClick()
        fun onPreviousPageClick()
    }

    val adapterListerner = adapterListerner

    var pageMovies : PageMovies? = null
        set(movies) {
            field = movies
            notifyDataSetChanged()

        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val movieView = layoutInflater.inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(movieView)
    }


    /**
     * We return the numbers of movies
     */
    override fun getItemCount(): Int {
        pageMovies?.let { return it.movies.size }
        return 0

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        pageMovies?.let{


            holder.previousButton.visibility = if(position==0 && it.pageID > 1)View.VISIBLE else View.GONE
            holder.nextButton.visibility = if(position==it.movies.size-1 && it.hasMore)View.VISIBLE else View.GONE

            if(position < it.movies.size) {

                val movie = it.movies[position]
                holder.ratingTextView.text = "rating : ${movie.rating.toString()}"
                holder.titleTextView.text = "title : ${movie.title}"
                Picasso.get().load(getUrlPoster(movie.poster))
                    .into(holder.posterImageView)


            }
        }
    }

    inner class MovieViewHolder: RecyclerView.ViewHolder{

        val titleTextView : TextView
        val ratingTextView : TextView
        val posterImageView:ImageView
        val nextButton: Button
        val previousButton: Button
        val itemViewGroup:ViewGroup

        constructor(itemView: View) : super(itemView){
            itemViewGroup = itemView as ViewGroup
            titleTextView   = itemViewGroup.findViewById(R.id.textTitle)
            ratingTextView  = itemViewGroup.findViewById(R.id.textRating)
            posterImageView = itemViewGroup.findViewById(R.id.imagePoster)
            nextButton      = itemViewGroup.findViewById(R.id.buttonNext)
            previousButton  = itemViewGroup.findViewById(R.id.buttonPrevious)

            nextButton.setOnClickListener({adapterListerner.onNextPageClick()})
            previousButton.setOnClickListener({adapterListerner.onPreviousPageClick()})
            itemView.setOnClickListener({adapterListerner.onListItemClick(adapterPosition)})
        }

    }
}