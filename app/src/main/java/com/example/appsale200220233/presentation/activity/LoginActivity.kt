package com.example.appsale200220233.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.appsale200220233.R
import com.example.appsale200220233.data.remote.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        CoroutineScope(Dispatchers.Default).launch {
            val apiService = RetrofitClient.getApiService()
            val hashMapUser = mutableMapOf<String, String>()
            hashMapUser["email"] = "android2002@gmail.com"
            hashMapUser["password"] = "1234567789"
            val result = withContext(Dispatchers.Default) { apiService.signIn(hashMapUser) }
            Log.d("BBB", result.toString())
        }

    }
}
