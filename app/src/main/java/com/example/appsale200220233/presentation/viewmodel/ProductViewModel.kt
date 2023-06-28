package com.example.appsale200220233.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsale200220233.common.AppConstant
import com.example.appsale200220233.common.extensions.launchOnMain
import com.example.appsale200220233.common.utils.ThrowableUtils
import com.example.appsale200220233.data.local.SharePreferenceApp
import com.example.appsale200220233.data.model.Order
import com.example.appsale200220233.data.model.Product
import com.example.appsale200220233.data.model.User
import com.example.appsale200220233.data.remote.AppResource
import com.example.appsale200220233.data.repository.OrderRepository
import com.example.appsale200220233.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by pphat on 6/26/2023.
 */
class ProductViewModel(
    private var productRepository: ProductRepository,
    private var orderRepository: OrderRepository,
) : ViewModel() {
    private var liveDataListProduct: MutableLiveData<AppResource<List<Product>>> = MutableLiveData()

    fun getLiveDataListProducts(): LiveData<AppResource<List<Product>>> {
        return liveDataListProduct
    }

    private var liveDataOrder: MutableLiveData<AppResource<Order>> = MutableLiveData()

    fun getLiveDataOrder(): LiveData<AppResource<Order>> {
        return liveDataOrder
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

    fun addProductToCart(idProduct: String) {
        liveDataOrder.value = AppResource.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = withContext(Dispatchers.Default) {
                    orderRepository.addCart(idProduct)
                }
                launchOnMain {
                    liveDataOrder.value = AppResource.SUCCESS(result.data)
                }
            } catch (e: Exception) {
                val errorResponse = ThrowableUtils.parseExceptionHttp(e)
                launchOnMain {
                    errorResponse ?: return@launchOnMain
                    liveDataOrder.value = AppResource.ERROR(errorResponse)
                }
            }
        }
    }

    fun getCart() {
        liveDataOrder.value = AppResource.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = withContext(Dispatchers.Default) {
                    orderRepository.fetchCart()
                }
                launchOnMain {
                    liveDataOrder.value = AppResource.SUCCESS(result.data)
                }
            } catch (e: Exception) {
                val errorResponse = ThrowableUtils.parseExceptionHttp(e)
                launchOnMain {
                    errorResponse ?: return@launchOnMain
                    liveDataOrder.value = AppResource.ERROR(errorResponse)
                }
            }
        }
    }
}
