package com.way.suitmediamobile.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.way.suitmediamobile.data.Repository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val repository: Repository,
    val app: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository, app) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel Class")
            }
        }
    }

}