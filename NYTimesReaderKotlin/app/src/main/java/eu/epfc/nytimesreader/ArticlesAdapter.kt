package eu.epfc.nytimesreader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.squareup.picasso.Picasso
import eu.epfc.nytimesreader.data.Article


class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>(){

    var articles : List<Article>? = null
        set(articleList) {
            field = articleList
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        // get a layoutInflater from the context attached to the parent view
        val layoutInflater = LayoutInflater.from(parent.context)

        // inflate the layout item_planet in a view
        val planetView = layoutInflater.inflate(R.layout.item_layout, parent, false)

        // create a new ViewHolder with the view planetView
        return ArticleViewHolder(planetView)
    }

    override fun getItemCount(): Int {

        val articleList = articles
        if (articleList != null) {
            return articleList.size
        }
        else
        {
            return 0
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        val articleList = articles
        if (articleList != null) {

            // get the corresponding planet data (based on the parameter position)
            val article = articleList[position]

            // get the TextView of the item
            val itemViewGroup = holder.itemView as ViewGroup

            val titleTextView : TextView = itemViewGroup.findViewById(R.id.text_item_title)
            titleTextView.text = article.title

            val abstractTextView : TextView = itemViewGroup.findViewById(R.id.text_item_abstract)
            abstractTextView.text = article.articleAbstract

            val imageView : ImageView = itemViewGroup.findViewById(R.id.article_image)
            Picasso.get().load(article.imageUrl).into(imageView)
        }
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}