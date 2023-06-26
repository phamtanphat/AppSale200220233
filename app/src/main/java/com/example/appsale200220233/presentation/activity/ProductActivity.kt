package com.example.appsale200220233.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.appsale200220233.R
import com.example.appsale200220233.common.utils.ToastUtils
import com.example.appsale200220233.data.remote.AppResource
import com.example.appsale200220233.data.repository.AuthenticationRepository
import com.example.appsale200220233.data.repository.ProductRepository
import com.example.appsale200220233.presentation.adapter.ProductAdapter
import com.example.appsale200220233.presentation.viewmodel.LoginViewModel
import com.example.appsale200220233.presentation.viewmodel.ProductViewModel

class ProductActivity : AppCompatActivity() {

    private var viewLoading: LinearLayout? = null
    private var productViewModel: ProductViewModel? = null
    private var recyclerViewProduct: RecyclerView? = null
    private var productAdapter : ProductAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        viewLoading = findViewById(R.id.layout_loading)
        recyclerViewProduct = findViewById(R.id.recycler_view_product)
        productAdapter = ProductAdapter(context = this)
        recyclerViewProduct?.adapter = productAdapter
        recyclerViewProduct?.hasFixedSize()

        productViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductViewModel(ProductRepository()) as T
            }
        })[ProductViewModel::class.java]

        productViewModel?.getLiveDataListProducts()?.observe(this) {
            when (it) {
                is AppResource.LOADING -> viewLoading?.isVisible = true
                is AppResource.ERROR -> {
                    viewLoading?.isVisible = false
                    ToastUtils.showToast(this@ProductActivity, it.error.message)
                }
                is AppResource.SUCCESS -> {
                    viewLoading?.isVisible = false
                    productAdapter?.updateListProduct(it.data)
                }
            }
        }

        productViewModel?.executeGetListProducts()
    }
}
