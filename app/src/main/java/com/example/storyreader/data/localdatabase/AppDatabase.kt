package com.example.storyreader.data.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.storyreader.data.localdatabase.models.CategoryDbModel
import com.example.storyreader.data.localdatabase.models.StoryCategoryCrossRef
import com.example.storyreader.data.localdatabase.models.StoryDbModel

@Database(
    entities = [
        StoryDbModel::class,
        CategoryDbModel::class,
        StoryCategoryCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        private var db: AppDatabase? = null
        private const val DB_NAME = "story.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun storyDao(): StoryDao
}