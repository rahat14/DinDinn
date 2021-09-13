package com.syntex_error.dindinn.ordersScreen

import com.syntex_error.dindinn.models.OrderResponse
import com.syntex_error.dindinn.network.ApiInterface
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

class OrderRepository(private val api: ApiInterface) {
    fun getOrders(): Observable<List<OrderResponse>> {
        return api.getAllOrders()
    }
}