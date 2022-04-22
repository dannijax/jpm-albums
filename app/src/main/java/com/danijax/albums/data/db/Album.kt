package com.danijax.albums.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "albums")
data class Album(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    @ColumnInfo(name = "userId") val userId: String = "",
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "DateAdded") var dateAdded: Date? = Date(),
    @ColumnInfo(name = "DateModified") var dateModified: Date? = Date()
)
