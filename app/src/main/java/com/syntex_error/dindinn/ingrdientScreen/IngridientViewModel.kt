package com.syntex_error.dindinn.ingrdientScreen

import android.content.Context
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syntex_error.dindinn.models.Food
import com.syntex_error.dindinn.models.Orders
import com.syntex_error.dindinn.models.category
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class IngridientViewModel @ViewModelInject constructor(
    repo: IngridientRepository,
    @ApplicationContext application: Context,
) : ViewModel() {

    val repository = repo
    private var ctx: Context? = application
    private var compositeDisposable = CompositeDisposable()
    private val categoryList = MutableLiveData<List<category>>()
    private val foodList = MutableLiveData<List<Food>>()

    fun getAllTheOrders() {
        compositeDisposable.add(
            repository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { cats ->
                        categoryList.value = cats
                    },
                    { throwable ->
                        showToast(throwable.message.toString())
                    }
                )
        )


    }

    fun getAllTheFoodOrders() {
        compositeDisposable.add(
            repository.getSingleCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { foods ->
                        foodList.value = foods
                    },
                    { throwable ->
                        showToast(throwable.message.toString())
                    }
                )
        )


    }

    fun showToast(msg: String) {
        Toast.makeText(ctx, "Error : $msg", Toast.LENGTH_SHORT).show()
    }

    fun getCats(): LiveData<List<category>> {
        return categoryList
    }

    fun getFoods(): LiveData<List<Food>> {
        return foodList
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}