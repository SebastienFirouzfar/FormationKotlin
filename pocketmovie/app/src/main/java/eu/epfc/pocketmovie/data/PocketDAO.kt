package eu.epfc.pocketmovie.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PocketDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMovieInPocket(movie:Movie)

    @Query("SELECT * FROM movies LIMIT (:page-1)*20,20")
    fun getMoviesInPocketByPage(page:Int):List<Movie>

    @Query("SELECT * FROM movies WHERE id = :movieID")
    fun getMovieByID(movieID:Long):Movie

    @Query("DELETE FROM movies WHERE id = :movieId")
    fun removeMovieInPocket(movieId: Long):Int
}

