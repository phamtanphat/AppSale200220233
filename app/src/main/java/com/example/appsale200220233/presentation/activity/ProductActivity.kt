package com.example.appsale200220233.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsale200220233.R
import com.example.appsale200220233.common.utils.ToastUtils
import com.example.appsale200220233.data.remote.AppResource
import com.example.appsale200220233.data.repository.AuthenticationRepository
import com.example.appsale200220233.data.repository.ProductRepository
import com.example.appsale200220233.presentation.viewmodel.LoginViewModel
import com.example.appsale200220233.presentation.viewmodel.ProductViewModel

class ProductActivity : AppCompatActivity() {

    private var productViewModel: ProductViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        productViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductViewModel(ProductRepository()) as T
            }
        })[ProductViewModel::class.java]

        productViewModel?.getLiveDataListProducts()?.observe(this) {
            when (it) {
                is AppResource.LOADING -> Log.d("BBB", "Loading")
                is AppResource.ERROR -> {
                    Log.d("BBB", it.error.message)
                }
                is AppResource.SUCCESS -> {
                    Log.d("BBB", it.data.toString())
                }
            }
        }

        productViewModel?.executeGetListProducts()
    }
}
