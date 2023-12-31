package com.example.appsale200220233.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsale200220233.common.extensions.launchOnMain
import com.example.appsale200220233.common.utils.ThrowableUtils
import com.example.appsale200220233.data.model.User
import com.example.appsale200220233.data.remote.AppResource
import com.example.appsale200220233.data.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by pphat on 6/23/2023.
 */
class RegisterViewModel(var respository: AuthenticationRepository) : ViewModel() {

    private var liveDataUser: MutableLiveData<AppResource<User>> = MutableLiveData()
    fun getLiveDataUser(): LiveData<AppResource<User>> {
        return liveDataUser
    }

    fun executeRegister(
        email: String,
        password: String,
        name: String,
        phone: String,
        address: String
    ) {
        liveDataUser.value = AppResource.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = withContext(Dispatchers.Default) {
                    respository.requestRegister(
                        email = email,
                        password = password,
                        name = name,
                        phone = phone,
                        address = address
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
