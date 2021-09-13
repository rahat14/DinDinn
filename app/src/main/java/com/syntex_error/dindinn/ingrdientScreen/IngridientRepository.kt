package com.syntex_error.dindinn.ingrdientScreen

import com.syntex_error.dindinn.models.Food
import com.syntex_error.dindinn.models.OrderResponse
import com.syntex_error.dindinn.models.category
import com.syntex_error.dindinn.network.ApiInterface
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

class IngridientRepository(private val api: ApiInterface) {
    fun getCategories(): Observable<List<category>> {
        return api.getAllCategory()
    }

    fun getSingleCategories(): Observable<List<Food>> {
        return api.getAllSingleCategory()
    }
}