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
                        //.addMigrations(MIGRATION_1_2)
                        //.addMigrations(MIGRATION_2_3)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

    }
}