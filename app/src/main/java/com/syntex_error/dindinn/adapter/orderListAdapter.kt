package com.syntex_error.dindinn.adapter

import android.content.Context
import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.syntex_error.dindinn.R
import com.syntex_error.dindinn.Utils
import com.syntex_error.dindinn.databinding.OrderItemBinding
import com.syntex_error.dindinn.models.Orders

class orderListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Orders>() {

        override fun areItemsTheSame(oldItem: Orders, newItem: Orders): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Orders, newItem: Orders): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return viewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.order_item,
                parent,
                false
            ),
            interaction,
            differ.currentList.size
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is viewholder -> {

                holder.bind(differ.currentList[position])

                holder.setIsRecyclable(false);

            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Orders>) {
        differ.submitList(list)
    }

    class viewholder
    constructor(
        itemView: View,
        private val interaction: Interaction?,
        size: Int
    ) : RecyclerView.ViewHolder(itemView) {

        private val binding = OrderItemBinding.bind(itemView)
        var Timer: CountDownTimer? = null
        val sizer = size
        val  mp : MediaPlayer?  = MediaPlayer.create(binding.root.context, R.raw.not)
        fun bind(item: Orders) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            binding.productName.text = " x ${item.quantity} ${item.title}"

            binding.orderID.text = "#${item.id}"
            binding.orderTime.text = "at ${Utils.dateConverter(item.created_at)}"
            binding.itemCount.text = " $sizer items"
            binding.timer.text = "${Utils.getInterval(item.alerted_at, item.expired_at)}"
            //Very important code for some problems
            Timer?.cancel() // cancelling the previous timer
            binding.pbar.max = Utils.getInterval(item.alerted_at, item.expired_at).toInt()
            Timer = object :
                CountDownTimer(Utils.getInterval(item.alerted_at, item.expired_at) * 1000, 1000) {
                override fun onTick(p0: Long) {
                    var diff = p0
                    val secondsInMilli: Long = 1000
                    val minutesInMilli = secondsInMilli * 60
                    val hoursInMilli = minutesInMilli * 60
                    val daysInMilli = hoursInMilli * 24

                    val elapsedDays = diff / daysInMilli
                    diff %= daysInMilli

                    val elapsedHours = diff / hoursInMilli
                    diff %= hoursInMilli

                    val elapsedMinutes = diff / minutesInMilli
                    diff %= minutesInMilli

                    val elapsedSeconds = diff / secondsInMilli
                    binding.timer.text = "$elapsedMinutes min $elapsedSeconds S"
                    binding.pbar.incrementProgressBy(1)

                }

                override fun onFinish() {
                    binding.timer.text = "00 S"
                    binding.accept.text = "Expired"
                    mp?.start()

                }

            }
            Timer?.start()



            setRcv(binding.itemList, item, binding.root.context)
        }

        fun setRcv(rec: RecyclerView, item: Orders, ctx: Context) {
            val mAdapter = AddonItemAdapter(item.addon)
            rec.apply {
                layoutManager = LinearLayoutManager(ctx)
                adapter = mAdapter
            }
            rec.adapter = mAdapter
        }
    }


    interface Interaction {
        fun onItemSelected(position: Int, item: Orders)
    }
}
