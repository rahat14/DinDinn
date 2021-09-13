package com.syntex_error.dindinn.ordersScreen

import android.content.Context
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syntex_error.dindinn.models.Orders
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class OrderViewModel @ViewModelInject constructor(
    repo: OrderRepository,
    @ApplicationContext application: Context,
) : ViewModel() {

    val repository = repo
    private var ctx: Context? = application
    private var compositeDisposable = CompositeDisposable()
    private val ordersList = MutableLiveData<List<Orders>>()

    fun getAllTheOrders() {
        compositeDisposable.add(
            repository.getOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { orders ->
                        if (orders[0].status.success) {
                            ordersList.value = orders[0].data
                        } else {
                            showToast("Something Went Wrong....")
                        }
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
    fun getOrders(): LiveData<List<Orders>> {
        return ordersList
    }
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}