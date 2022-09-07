package com.way.suitmediamobile.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.way.suitmediamobile.data.local.model.GuestEntity
import com.way.suitmediamobile.data.local.model.PersonEntity
import com.way.suitmediamobile.data.local.utils.GuestConverters
import com.way.suitmediamobile.data.local.utils.PictureConverters

@Database(
    entities = [PersonEntity::class, GuestEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    PictureConverters::class, GuestConverters::class
)
abstract class PersonDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}