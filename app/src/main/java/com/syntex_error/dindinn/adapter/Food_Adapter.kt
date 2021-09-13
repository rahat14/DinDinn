package com.syntex_error.dindinn.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.syntex_error.dindinn.R
import com.syntex_error.dindinn.databinding.IngridientItemBinding
import com.syntex_error.dindinn.databinding.OrderItemBinding
import com.syntex_error.dindinn.models.Food

class Food_Adapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Food>() {

        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return  oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return  oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return viewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.ingridient_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is viewholder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Food>) {
        differ.submitList(list)
    }

    class viewholder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = IngridientItemBinding.bind(itemView)
        fun bind(item: Food) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            binding.title.text = item.name
            binding.qty.text = item.qty.toString()
            Glide.with(binding.root.context)
                .load(item.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageview)

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Food)
    }
}
