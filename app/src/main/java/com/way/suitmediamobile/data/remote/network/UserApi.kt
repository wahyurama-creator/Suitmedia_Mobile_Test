package com.way.suitmediamobile.data.remote.network

import com.way.suitmediamobile.data.remote.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface UserApi {

    @GET("users")
    suspend fun getUsers(
        @QueryMap queries: Map<String, Int>
    ): Response<UserResponse>
}