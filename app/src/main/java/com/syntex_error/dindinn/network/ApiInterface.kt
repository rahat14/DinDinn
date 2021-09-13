package com.syntex_error.dindinn.network

import com.google.gson.JsonObject
import com.syntex_error.dindinn.models.Food
import com.syntex_error.dindinn.models.OrderResponse
import com.syntex_error.dindinn.models.category
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {


    @GET("orders")
    fun getAllOrders(
    ): Observable<List<OrderResponse>>// its expecting a list because my mock api server did not let me send only

    // on object they need to send an array
    @GET("category")
    fun getAllCategory(
    ): Observable<List<category>>

    // i used mocker api but it dont support any relation
    // thats why i had to call same endpoint
    @GET("single-category")
    fun getAllSingleCategory(
    ): Observable<List<Food>>

}