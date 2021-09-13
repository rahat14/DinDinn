package com.syntex_error.dindinn.ordersScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.syntex_error.dindinn.R
import com.syntex_error.dindinn.adapter.orderListAdapter
import com.syntex_error.dindinn.databinding.ActivityMainBinding
import com.syntex_error.dindinn.ingrdientScreen.IngridientScreen
import com.syntex_error.dindinn.models.Orders
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), orderListAdapter.Interaction {
    private val viewModel by viewModels<OrderViewModel>()
    private lateinit var mAdapter: orderListAdapter

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAdapter = orderListAdapter(this)


        binding.orderList.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = mAdapter

        }
        binding.orderList.setHasFixedSize(true)



        callForOrder()

        binding.ingredientList.setOnClickListener {
            startActivity(Intent(applicationContext  , IngridientScreen::class.java))
        }

    }

    private fun callForOrder() {
        viewModel.getOrders().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                // reder list
                mAdapter.submitList(it)
            }

        })


        viewModel.getAllTheOrders()

    }

    override fun onItemSelected(position: Int, item: Orders) {

    }

}