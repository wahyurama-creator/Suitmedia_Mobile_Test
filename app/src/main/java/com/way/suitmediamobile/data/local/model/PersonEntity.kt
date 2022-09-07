package com.way.suitmediamobile.data.local.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_table")
data class PersonEntity(
    val name: String,
    val picture: Bitmap
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
