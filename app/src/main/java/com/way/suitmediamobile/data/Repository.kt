package com.way.suitmediamobile.data

import com.way.suitmediamobile.data.local.LocalDataSource
import com.way.suitmediamobile.data.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
)