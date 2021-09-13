package com.syntex_error.dindinn.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.syntex_error.dindinn.ingrdientScreen.fragment.IngrdientListsFragment

class IngridientPagerAdapter (
    private val fragmentList: MutableList<Pair<String, IngrdientListsFragment>>,
    fragment: FragmentActivity
) : FragmentStateAdapter(fragment) {

//    private var pageIds = fragmentList.map { fragmentList.hashCode().toLong() }

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): IngrdientListsFragment {
        return fragmentList[position].second
    }

//    override fun getItemId(position: Int): Long = pageIds[position] // Make sure notifyDataSetChanged() works

//    override fun containsItem(itemId: Long): Boolean = pageIds.contains(itemId)

    fun getFragmentName(position: Int) = fragmentList[position].first

    fun addFragment(fragment: Pair<String, IngrdientListsFragment>) {
        fragmentList.add(fragment)
        notifyDataSetChanged()
    }

    fun removeFragment(position: Int) {
        fragmentList.removeAt(position)
        notifyDataSetChanged()
    }

}