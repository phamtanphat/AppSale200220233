package com.example.appsale200220233.presentation.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsale200220233.R
import com.example.appsale200220233.data.remote.ApiService
import com.example.appsale200220233.data.remote.AppResource
import com.example.appsale200220233.data.remote.RetrofitClient
import com.example.appsale200220233.data.repository.AuthenticationRepository
import com.example.appsale200220233.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private var loginViewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProvider(this@LoginActivity, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(AuthenticationRepository()) as T
            }
        })[LoginViewModel::class.java]

        loginViewModel?.getLiveDataUser()?.observe(this@LoginActivity) {
            when(it) {
                is AppResource.LOADING -> Log.d("BBB", "Loading")
                is AppResource.ERROR -> Log.d("BBB", "Error ${it.message}")
                is AppResource.SUCCESS -> Log.d("BBB", "Success ${it.data}")
            }
        }

        loginViewModel?.executeLogin("android002@gmail.com", "1234567789")
    }
}
