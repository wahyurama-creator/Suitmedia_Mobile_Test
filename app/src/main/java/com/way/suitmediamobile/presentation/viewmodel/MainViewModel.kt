package com.way.suitmediamobile.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.lifecycle.*
import com.way.suitmediamobile.data.Repository
import com.way.suitmediamobile.data.local.model.GuestEntity
import com.way.suitmediamobile.data.local.model.PersonEntity
import com.way.suitmediamobile.data.remote.model.UserResponse
import com.way.suitmediamobile.data.remote.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    val app: Application
) : AndroidViewModel(app) {

    // Local variable
    val readPersonEntity: LiveData<List<PersonEntity>> =
        repository.localDataSource.readPerson().asLiveData()
    val readGuestsEntity: LiveData<List<GuestEntity>> =
        repository.localDataSource.readGuests().asLiveData()

    // Remote variable
    val userResponse: MutableLiveData<NetworkResult<UserResponse>> = MutableLiveData()

    // One fragment
    fun insertPerson(personEntity: PersonEntity) = viewModelScope.launch {
        repository.localDataSource.insertPerson(personEntity)
    }

    fun validateInput(textName: String? = null, palindromeText: String? = null): Boolean {
        if (!textName.isNullOrEmpty()) {
            return true
        }
        return if (!palindromeText.isNullOrEmpty()) {
            true
        } else {
            Toast.makeText(app.applicationContext, "Filled cannot be blank!", Toast.LENGTH_SHORT)
                .show()
            false
        }
    }

    fun isPalindrome(value: String): Boolean {
        for (i in value.indices) {
            val lastIndex = value.length - i - 1

            if (value[i] != value[lastIndex]) {
                Toast.makeText(app.applicationContext, "Not palindrome!", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        Toast.makeText(app.applicationContext, "Is palindrome!", Toast.LENGTH_SHORT).show()
        return true
    }

    // Four screen
    fun getUsers(queries: Map<String, Int>) = viewModelScope.launch {
        getUsersSafeCall(queries)
    }

    private suspend fun getUsersSafeCall(queries: Map<String, Int>) {
        userResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remoteDataSource.getUsers(queries)
                userResponse.value = handleUsersResponse(response)

                val guestList = userResponse.value?.data
                if (guestList != null) {
                    offlineCacheGuests(guestList)
                }
            } catch (e: Exception) {
                userResponse.value = NetworkResult.Error(e.message.toString())
            }
        } else {
            userResponse.value = NetworkResult.Error("No Internet connection")
        }
    }

    private fun insertGuests(guestEntity: GuestEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.localDataSource.insertGuests(guestEntity)
    }

    private fun offlineCacheGuests(userResponse: UserResponse) {
        val guestEntity = GuestEntity(userResponse)
        insertGuests(guestEntity)
    }


    private fun handleUsersResponse(response: Response<UserResponse>): NetworkResult<UserResponse> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout error")
            }
            response.body()!!.data.isEmpty() -> {
                return NetworkResult.Error("Users not found")
            }
            response.isSuccessful -> {
                val recipes = response.body()
                return if (recipes != null) {
                    NetworkResult.Success(recipes)
                } else {
                    NetworkResult.Error(response.message())
                }
            }
            else -> return NetworkResult.Error(response.message())
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}