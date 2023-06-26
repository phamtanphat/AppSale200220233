package com.example.appsale200220233.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsale200220233.common.AppConstant
import com.example.appsale200220233.common.extensions.launchOnMain
import com.example.appsale200220233.common.utils.ThrowableUtils
import com.example.appsale200220233.data.local.SharePreferenceApp
import com.example.appsale200220233.data.model.Product
import com.example.appsale200220233.data.model.User
import com.example.appsale200220233.data.remote.AppResource
import com.example.appsale200220233.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by pphat on 6/26/2023.
 */
class ProductViewModel(
    private var productRepository: ProductRepository
) : ViewModel() {
    private var liveDataListProduct: MutableLiveData<AppResource<List<Product>>> = MutableLiveData()

    fun getLiveDataListProducts(): LiveData<AppResource<List<Product>>> {
        return liveDataListProduct
    }

    fun executeGetListProducts() {
        liveDataListProduct.value = AppResource.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = withContext(Dispatchers.Default) {
                    productRepository.getListProduct()
                }
                launchOnMain {
                    liveDataListProduct.value = AppResource.SUCCESS(result.data)
                }
            } catch (e: Exception) {
                val errorResponse = ThrowableUtils.parseExceptionHttp(e)
                launchOnMain {
                    errorResponse ?: return@launchOnMain
                    liveDataListProduct.value = AppResource.ERROR(errorResponse)
                }
            }
        }
    }
}
