package eu.epfc.pocketmovie.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

class PageMovies(val pageID:Int,val movies:List<Movie>,val hasMore:Boolean=true) {
}

class MovieDetail(
            val id: Long
            ,val title:String
            ,val rating:String
            ,val poster:String
            ,val overview:String
            ,val releaseDate:String)


@Entity(tableName = "movies")
class Movie(
            @PrimaryKey(autoGenerate = false)
            val id: Long
            ,val title:String
            ,val rating:String
            ,val poster:String)


class VideoDetail(val id: String,val key:String, val site:String,val type : String)