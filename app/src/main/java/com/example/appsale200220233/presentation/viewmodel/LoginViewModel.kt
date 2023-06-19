package com.example.appsale200220233.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appsale200220233.common.extensions.launchOnBackground
import com.example.appsale200220233.data.model.User
import com.example.appsale200220233.data.remote.AppResource
import com.example.appsale200220233.data.repository.AuthenticationRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by pphat on 6/19/2023.
 */
class LoginViewModel(var respository: AuthenticationRepository): ViewModel() {

    private var liveDataUser: MutableLiveData<AppResource<User>> = MutableLiveData()

    fun getLiveDataUser(): LiveData<AppResource<User>> {
        return liveDataUser
    }

    fun executeLogin(email: String, password: String) {
        liveDataUser.value = AppResource.LOADING
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            liveDataUser.value = AppResource.ERROR(throwable)
        }

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            try {
                val defer = async { respository.requestLogin(email, password) }
                val result = defer.await()
                withContext(Dispatchers.Main) {
                    liveDataUser.value = AppResource.SUCCESS(result.data)
                }
            } catch (e: Exception) {
                Log.d("BBB", e.message.toString())
            }
        }
    }
}
