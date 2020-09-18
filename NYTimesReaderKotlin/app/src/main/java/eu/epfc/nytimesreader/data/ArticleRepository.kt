package eu.epfc.nytimesreader.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import eu.epfc.nytimesreader.HttpRequestService
import org.json.JSONException
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.util.ArrayList

class ArticleRepository{

    private val urlNYTimesTopStories = "https://api.nytimes.com/svc/topstories/v2/technology.json?api-key=W5WCAfqpci5w1i4VPZBWB0YK62YDJxGb"
    private var httpReceiverInitialized = false
    var articles: List<Article>? = null
        private set // public but read-only

    private var dataUIListener: WeakReference<DataUIListener>? = null

    interface DataUIListener {
        fun updateUI()
    }

    private inner class HttpReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            if (intent.action == "httpRequestComplete") {
                // extract JSON response from the intent
                val response = intent.getStringExtra("responseString")

                // parse Articles from JSON
                articles = ArticleRepository.instance.parseTopStoriesResponse(response)

                // save to Database
                val articleList = articles
                if (articleList != null) {
                    val articleDAO = ArticleDatabase.getInstance(context).articleDao()
                    articleDAO.bulkInsert(articleList)
                }

            } else if (intent.action == "httpRequestError") {
                // connexion error -> get articles from database
                val articleDAO = ArticleDatabase.getInstance(context).articleDao()
                articles = articleDAO.getAllArticles()
            }
            // update the UI
            val myDataUIListener = dataUIListener
            if (myDataUIListener != null) {
                myDataUIListener.get()?.updateUI()
            }
        }
    }

    private fun initHttpReceiverWithContext(context: Context) {
        // register the broadcast receiver to intent with action "httpRequestComplete" and "httpRequestError"
        val intentFilter = IntentFilter()
        intentFilter.addAction("httpRequestComplete")
        intentFilter.addAction("httpRequestError")
        val httpReceiver = HttpReceiver()
        context.applicationContext.registerReceiver(httpReceiver, intentFilter)
    }

    fun observe(dataUIListener: DataUIListener) {
        this.dataUIListener = WeakReference(dataUIListener)
    }

    /**
     * Launch asynchronously a request to retrieve articles from web service. When finished,
     * updateUI() will be called on the DataUIListener instance on the UI thread.
     */
    fun fetchArticles(context: Context) {

        if(!httpReceiverInitialized){
            initHttpReceiverWithContext(context.applicationContext)
            httpReceiverInitialized = true
        }

        // if there is no articles stored in ArticleManager
        if (articles == null) {
            // get new articles from the server
            HttpRequestService.startActionRequestHttp(context.applicationContext, urlNYTimesTopStories)
        } else {
            val myDataUIListener = dataUIListener
            if (myDataUIListener != null){
                myDataUIListener.get()?.updateUI()
            }

        }
    }


    /**
     * Parse a json string received from NYTime web service
     * @param jsonString : a json string received from the server
     * @return : a list of Article objects parsed by the method
     */
    private fun parseTopStoriesResponse(jsonString: String): ArrayList<Article> {
        val newArticles = ArrayList<Article>()

        try {
            val jsonObject = JSONObject(jsonString)
            val jsonArticles = jsonObject.getJSONArray("results")

            for (i in 0 until jsonArticles.length()) {
                val jsonArticle = jsonArticles.getJSONObject(i)

                val title = jsonArticle.getString("title")
                val articleAbstract = jsonArticle.getString("abstract")

                var thumbnailUrl = ""
                val multimediaJson = jsonArticle.getJSONArray("multimedia")
                if (multimediaJson.length() > 0) {
                    val multimediaItem = multimediaJson.getJSONObject(0)
                    thumbnailUrl = multimediaItem.getString("url")
                }

                val newArticle = Article(title, articleAbstract,thumbnailUrl)

                newArticles.add(newArticle)
            }

        } catch (e: JSONException) {
            Log.e("ArticleRepository", "can't parse json string correctly")
            e.printStackTrace()
        }

        return newArticles
    }

    companion object {

        /**
         * return the unique static instance of the singleton
         * @return an ArticleManager instance
         */
        val instance = ArticleRepository()
    }
}