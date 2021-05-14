package com.example.mad03_fragments_and_navigation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mad03_fragments_and_navigation.models.Movie

@Database(entities = [Movie::class], version = 3, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    // TODO implement me

    abstract val movieDao: MovieDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(context, AppDatabase::class.java, "movieDb")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}