package com.syntex_error.dindinn.ingrdientScreen.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.syntex_error.dindinn.adapter.Food_Adapter
import com.syntex_error.dindinn.databinding.FragmentIngrdientListsBinding
import com.syntex_error.dindinn.ingrdientScreen.IngridientRepository
import com.syntex_error.dindinn.models.Food
import com.syntex_error.dindinn.network.ApiInterface
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class IngrdientListsFragment(s: String, i: String) : Fragment(), Food_Adapter.Interaction {
    private lateinit var binding: FragmentIngrdientListsBinding

    @Inject
    lateinit var myRepository: IngridientRepository

    @Inject
    lateinit var api: ApiInterface
    private lateinit var mAdapter: Food_Adapter

    private var compositeDisposable = CompositeDisposable()
    private val cat_id = i
    private val cat_name = s

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentIngrdientListsBinding.inflate(layoutInflater, container, false)
        mAdapter = Food_Adapter(this)
        myRepository = IngridientRepository(api)
        setView()
        Toast.makeText(
            context,
            "Showing -> id  $cat_id  category name  -> $cat_name",
            Toast.LENGTH_SHORT
        ).show()
        /*
        as we got the category id and name we can call them from here
        as i used mock api it dont have any relational database feature
        so i will call the same api from here

         */


        callForFoodData(cat_id)




        return binding.root
    }

    private fun setView() {
        binding.list.apply {
            adapter = mAdapter
            layoutManager = GridLayoutManager(context , 2 )
        }
    }


    private fun callForFoodData(cat_id: String) {
        compositeDisposable.add(
            myRepository.getSingleCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { foods ->
                        mAdapter.submitList(foods)
                    },
                    { throwable ->

                    }
                )
        )


    }

    override fun onItemSelected(position: Int, item: Food) {

    }

}