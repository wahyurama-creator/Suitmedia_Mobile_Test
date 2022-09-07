package com.way.suitmediamobile.data.local.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.way.suitmediamobile.data.remote.model.UserResponse

class GuestConverters {

    private lateinit var gson: Gson

    @TypeConverter
    fun foodRecipeToString(userResponse: UserResponse): String {
        gson = Gson()
        return gson.toJson(userResponse)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): UserResponse {
        gson = Gson()
        val listOfType = object : TypeToken<UserResponse>() {}.type
        return gson.fromJson(data, listOfType)
    }
}