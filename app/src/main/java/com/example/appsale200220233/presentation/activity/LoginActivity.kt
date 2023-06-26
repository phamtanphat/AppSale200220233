package com.example.appsale200220233.presentation.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsale200220233.R
import com.example.appsale200220233.common.utils.SpannedUtils
import com.example.appsale200220233.common.utils.ToastUtils
import com.example.appsale200220233.data.remote.AppResource
import com.example.appsale200220233.data.repository.AuthenticationRepository
import com.example.appsale200220233.presentation.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private var loginViewModel: LoginViewModel? = null
    private var textEditEmail: TextInputEditText? = null
    private var textEditPassword: TextInputEditText? = null
    private var tvNavigateRegisterActivity: TextView? = null
    private var buttonSignIn: LinearLayout? = null
    private var viewLoading: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()

        loginViewModel = ViewModelProvider(this@LoginActivity, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(AuthenticationRepository()) as T
            }
        })[LoginViewModel::class.java]

        loginViewModel?.getLiveDataUser()?.observe(this@LoginActivity) {
            when (it) {
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

    private fun initView() {
        textEditEmail = findViewById(R.id.textEditEmail)
        textEditPassword = findViewById(R.id.textEditPassword)
        buttonSignIn = findViewById(R.id.button_sign_in)
        viewLoading = findViewById(R.id.layout_loading)
        tvNavigateRegisterActivity = findViewById(R.id.text_view_navigate_register_activity)

        // Set text register can click
        makeTextNavigateRegisterActivity()
    }

    private fun makeTextNavigateRegisterActivity() {
        SpannableStringBuilder().apply {
            append("Don't have an account?")
            append(SpannedUtils.setClickColorLink(
                text = "Register",
                context = this@LoginActivity
            ) {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            })
            tvNavigateRegisterActivity?.text = this
            tvNavigateRegisterActivity?.highlightColor = Color.TRANSPARENT
            tvNavigateRegisterActivity?.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}
