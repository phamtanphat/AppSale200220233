package com.example.appsale200220233.presentation.activity

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsale200220233.R
import com.example.appsale200220233.common.utils.ToastUtils
import com.example.appsale200220233.data.remote.AppResource
import com.example.appsale200220233.data.repository.AuthenticationRepository
import com.example.appsale200220233.presentation.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private var loginViewModel: LoginViewModel? = null
    private var textEditEmail: TextInputEditText? = null
    private var textEditPassword: TextInputEditText? = null
    private var buttonSignIn: LinearLayout? = null
    private var viewLoading: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        textEditEmail = findViewById(R.id.textEditEmail)
        textEditPassword = findViewById(R.id.textEditPassword)
        buttonSignIn = findViewById(R.id.button_sign_in)
        viewLoading = findViewById(R.id.layout_loading)

        loginViewModel = ViewModelProvider(this@LoginActivity, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(AuthenticationRepository()) as T
            }
        })[LoginViewModel::class.java]

        loginViewModel?.getLiveDataUser()?.observe(this@LoginActivity) {
            when(it) {
                is AppResource.LOADING -> viewLoading?.isVisible = true
                is AppResource.ERROR -> {
                    viewLoading?.isVisible = false
                    ToastUtils.showToast(this@LoginActivity, it.error.message)
                }
                is AppResource.SUCCESS -> {
                    viewLoading?.isVisible = false
                    ToastUtils.showToast(this@LoginActivity, "Login is successfully")
                }
            }
        }

        buttonSignIn?.setOnClickListener {
            val email = textEditEmail?.text.toString()
            val password = textEditPassword?.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                ToastUtils.showToast(this@LoginActivity, "Email or password is empty")
                return@setOnClickListener
            }

            loginViewModel?.executeLogin(email, password)
        }
    }
}
