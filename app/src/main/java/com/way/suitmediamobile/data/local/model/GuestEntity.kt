package com.way.suitmediamobile.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.way.suitmediamobile.data.remote.model.UserResponse

@Entity(tableName = "guest_table")
class GuestEntity (var userResponse: UserResponse) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}