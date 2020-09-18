package eu.epfc.nytimesreader.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
class Article(val title : String,
              val articleAbstract : String,
              val imageUrl : String,
              @PrimaryKey(autoGenerate=true) val articleId : Int = 0)
