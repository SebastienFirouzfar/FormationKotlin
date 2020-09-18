package eu.epfc.nytimesreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.epfc.nytimesreader.data.ArticleRepository

class MainActivity : AppCompatActivity(), ArticleRepository.DataUIListener {

    private lateinit var articlesAdapter : ArticlesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.list_articles)

        // set the adapter of the RecyclerView
        articlesAdapter = ArticlesAdapter()
        recyclerView.adapter = articlesAdapter

        // set the layoutManager of the recyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        ArticleRepository.instance.observe(this)
    }

    override fun onStart() {
        super.onStart()
        ArticleRepository.instance.fetchArticles(this)
    }

    override fun updateUI() {
        val articleList = ArticleRepository.instance.articles
        articlesAdapter.articles = articleList
    }
}
