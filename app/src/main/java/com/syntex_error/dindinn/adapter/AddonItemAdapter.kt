package com.syntex_error.dindinn.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.syntex_error.dindinn.R
import com.syntex_error.dindinn.databinding.OrderRowsBinding
import com.syntex_error.dindinn.models.Addon
import java.lang.StringBuilder

class AddonItemAdapter(val items: List<Addon>) :
    RecyclerView.Adapter<AddonItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.order_rows, parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], position)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Addon, pos: Int) = with(itemView) {

            val binding = OrderRowsBinding.bind(itemView)


            var addons: StringBuilder = StringBuilder()


                addons.append(item.quantity)
                addons.append(" ")
                addons.append(item.title)
                addons.append("\n")



            binding.addonName.text = addons.toString()

        }
    }
}

