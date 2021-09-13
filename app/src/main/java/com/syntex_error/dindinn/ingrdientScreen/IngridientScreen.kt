package com.syntex_error.dindinn.ingrdientScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.syntex_error.dindinn.adapter.IngridientPagerAdapter
import com.syntex_error.dindinn.databinding.ActivityIngridientScreenBinding
import com.syntex_error.dindinn.ingrdientScreen.fragment.IngrdientListsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IngridientScreen : AppCompatActivity() {
    private val viewModel by viewModels<IngridientViewModel>()
    private lateinit var binding: ActivityIngridientScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngridientScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // need to call for the api to get category list and add multiple categories


        callCategories()


    }

    private fun setTab(fragList: MutableList<Pair<String, IngrdientListsFragment>>) {

//        val fragList: MutableList<Pair<String, IngrdientListsFragment>> = arrayListOf()
//
//
//        fragList.add(Pair("OLd POSTy", IngrdientListsFragment("OLD", 12)))
//        fragList.add(Pair("Profile", IngrdientListsFragment("PROFILE", 10)))

        binding.pager.adapter = IngridientPagerAdapter(
            fragList,
            this
        )

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = (fragList[position].first)
        }.attach()


    }

    private fun callCategories() {
        viewModel.getCats().observe(this, {
            val fragList: MutableList<Pair<String, IngrdientListsFragment>> = arrayListOf()
            if (it.isNotEmpty()) {
                // loop the list and create a list of category
                fragList.clear()
                for (item in it) {
                    fragList.add(Pair(item.name, IngrdientListsFragment(item.name, item.id)))
                }

                setTab(fragList)
            }

        })


        viewModel.getAllTheOrders()

    }
}