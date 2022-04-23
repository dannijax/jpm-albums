package com.danijax.albums.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Album::class], version = 1, exportSchema = true)
@TypeConverters(DateConverter::class)
abstract class AlbumsDatabase: RoomDatabase() {

    abstract fun albumDao(): AlbumDao

    companion object {
        private var INSTANCE: AlbumsDatabase? = null

        fun getInstance(context: Context): AlbumsDatabase? {
            if (INSTANCE == null) {
                synchronized(AlbumsDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context, AlbumsDatabase::class.java, "albums.db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()//for no this is what we need when a user
                        //uninstalls application. Migrations can be provided if needed via the
                        // Migration Object

                        .build()
                }
            }
            return INSTANCE
        }

    }
}