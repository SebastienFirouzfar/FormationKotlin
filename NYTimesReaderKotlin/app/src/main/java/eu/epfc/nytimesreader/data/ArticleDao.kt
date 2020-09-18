package eu.epfc.nytimesreader.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(article: List<Article>)
    @Query("SELECT * FROM articles")
    fun getAllArticles(): List<Article>
}