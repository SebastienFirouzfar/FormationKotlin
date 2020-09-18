package eu.epfc.nytimesreader.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class  ArticleDatabase() : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "NYTimesReaderDB"
        private var sInstance: ArticleDatabase? = null

        fun getInstance(context: Context): ArticleDatabase {

            if (sInstance == null) {

                val dbBuilder = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java,
                    DATABASE_NAME
                )
                dbBuilder.allowMainThreadQueries()
                sInstance = dbBuilder.build()
            }
            return sInstance!!
        }
    }

    abstract fun articleDao(): ArticleDao
}