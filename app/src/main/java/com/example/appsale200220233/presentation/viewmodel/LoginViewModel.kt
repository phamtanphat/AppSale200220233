package com.example.appsale200220233.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsale200220233.common.extensions.launchOnMain
import com.example.appsale200220233.common.utils.ThrowableUtils
import com.example.appsale200220233.data.model.User
import com.example.appsale200220233.data.remote.AppResource
import com.example.appsale200220233.data.repository.AuthenticationRepository
import kotlinx.coroutines.*
import retrofit2.HttpException

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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = withContext(Dispatchers.Default) {
                    respository.requestLogin(
                        email = email,
                        password = password
                    )
                }
                launchOnMain { liveDataUser.value = AppResource.SUCCESS(result.data) }
            } catch (e: Exception) {
                val errorResponse = ThrowableUtils.parseExceptionHttp(e)
                launchOnMain {
                    errorResponse ?: return@launchOnMain
                    liveDataUser.value = AppResource.ERROR(errorResponse)
                }
            }
        }
    }
}
