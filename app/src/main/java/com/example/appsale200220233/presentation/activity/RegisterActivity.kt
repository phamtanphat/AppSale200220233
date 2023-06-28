package com.example.appsale200220233.presentation.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsale200220233.R
import com.example.appsale200220233.common.utils.SpannedUtils
import com.example.appsale200220233.common.utils.ToastUtils
import com.example.appsale200220233.data.remote.AppResource
import com.example.appsale200220233.data.repository.AuthenticationRepository
import com.example.appsale200220233.presentation.viewmodel.RegisterViewModel
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    private var textEditTextName: TextInputEditText? = null
    private var textEditTextLocation: TextInputEditText? = null
    private var textEditTextEmail: TextInputEditText? = null
    private var textEditTextPhone: TextInputEditText? = null
    private var textEditTextPassword: TextInputEditText? = null
    private var tvPopToLoginActivity: TextView? = null
    private var buttonRegister: LinearLayout? = null
    private var viewLoading: LinearLayout? = null
    private var registerViewModel: RegisterViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        textEditTextName = findViewById(R.id.textEditName)
        textEditTextLocation = findViewById(R.id.text_edit_address)
        textEditTextEmail = findViewById(R.id.textEditEmail)
        textEditTextPhone = findViewById(R.id.textEditPhone)
        textEditTextPassword = findViewById(R.id.textEditPassword)
        buttonRegister = findViewById(R.id.register_button)
        viewLoading = findViewById(R.id.layout_loading)
        tvPopToLoginActivity = findViewById(R.id.text_view_pop_to_login_activity)

        registerViewModel = ViewModelProvider(this@RegisterActivity, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RegisterViewModel(AuthenticationRepository(this@RegisterActivity)) as T
            }
        })[RegisterViewModel::class.java]

        // Action pop to Login Activity
        makeTextPopToLoginActivity()

        registerViewModel?.getLiveDataUser()?.observe(this@RegisterActivity) {
            when (it) {
                is AppResource.LOADING -> viewLoading?.isVisible = true
                is AppResource.ERROR -> {
                    viewLoading?.isVisible = false
                    ToastUtils.showToast(this@RegisterActivity, it.error.message)
                }
                is AppResource.SUCCESS -> {
                    viewLoading?.isVisible = false
                    ToastUtils.showToast(this@RegisterActivity, "Register is successfully")
                    finish()
                }
            }
        }

        buttonRegister?.setOnClickListener {
            val name = textEditTextName?.text.toString()
            val location = textEditTextLocation?.text.toString()
            val email = textEditTextEmail?.text.toString()
            val phone = textEditTextPhone?.text.toString()
            val password = textEditTextPassword?.text.toString()
            if (email.isEmpty() || password.isEmpty() || name.isEmpty() || location.isEmpty() || phone.isEmpty()) {
                ToastUtils.showToast(this@RegisterActivity, "Input is not valid")
                return@setOnClickListener
            }

            registerViewModel?.executeRegister(
                email = email,
                password = password,
                name = name,
                phone = phone,
                address = location
            )
        }
    }

    private fun makeTextPopToLoginActivity() {
        SpannableStringBuilder().apply {
            append("Don't have an account?")
            append(
                SpannedUtils.setClickColorLink(
                text = "Register",
                context = this@RegisterActivity
            ) {
                finish()
            })
            tvPopToLoginActivity?.text = this
            tvPopToLoginActivity?.highlightColor = Color.TRANSPARENT
            tvPopToLoginActivity?.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}
