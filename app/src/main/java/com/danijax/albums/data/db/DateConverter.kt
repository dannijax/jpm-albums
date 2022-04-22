package com.danijax.albums.data.db

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object DateConverter {

    @TypeConverter
    fun toDate(date: Long): Date {
        return Date(date)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }
}