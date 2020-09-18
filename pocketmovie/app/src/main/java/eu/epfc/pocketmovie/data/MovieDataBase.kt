package eu.epfc.pocketmovie.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDataBase():RoomDatabase(){

    companion object {

        private const val DATABASE_NAME = "PocketMovieDB"
        private var sInstance: MovieDataBase? = null

        fun getInstance(context: Context): MovieDataBase {
            if (sInstance == null) {
                val dbBuilder = Room.databaseBuilder (
                    context.applicationContext,
                    MovieDataBase::class.java,
                    DATABASE_NAME
                )
                dbBuilder.allowMainThreadQueries()
                sInstance = dbBuilder.build()
            }
            return sInstance!!
        }
    }

    abstract fun pocketDAO(): PocketDAO
}