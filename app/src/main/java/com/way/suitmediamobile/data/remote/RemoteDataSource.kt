package com.way.suitmediamobile.data.remote

import com.way.suitmediamobile.data.remote.model.UserResponse
import com.way.suitmediamobile.data.remote.network.UserApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val userApi: UserApi
) {

    suspend fun getUsers(queries: Map<String, Int>): Response<UserResponse> =
        userApi.getUsers(queries)

}