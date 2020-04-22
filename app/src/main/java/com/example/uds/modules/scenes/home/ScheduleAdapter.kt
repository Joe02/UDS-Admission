package com.example.uds.modules.scenes.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.uds.R
import com.example.uds.databinding.ItemScheduleBinding
import com.example.uds.helpers.recyclerview_helper.ItemClickListener
import com.example.uds.models.Schedule

class ScheduleAdapter(var schedules: List<Schedule>) : RecyclerView.Adapter<ScheduleAdapter.ItemViewHolder> () {

    var itemClickListener: ItemClickListener? = null
    private var inflater: LayoutInflater? = null
    private var parent: ViewGroup? = null

    fun setOnItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class ItemViewHolder(binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        val scheduleTitle: TextView = binding.scheduleTitle
        val scheduleDescription: TextView = binding.scheduleDescription
        val scheduleAuthor: TextView = binding.scheduleAuthor
        val scheduleCard: CardView = binding.scheduleCard

        override fun onClick(v: View?) {
            itemClickListener?.onItemClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        this.parent = parent
        val inflater = this.inflater ?: LayoutInflater.from(parent.context)

        if (this.inflater == null) {
            this.inflater = inflater
        }

        val scheduleBinding = DataBindingUtil.inflate<ItemScheduleBinding>(inflater, R.layout.item_schedule, parent,false)

        return ItemViewHolder(scheduleBinding)
    }

    override fun getItemCount(): Int = schedules.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.scheduleCard.setOnClickListener{

            if (schedules[position].isExpanded) {
                schedules[position].isExpanded = false
                holder.scheduleDescription.maxLines = 1
                holder.scheduleAuthor.visibility = View.GONE
            } else {
                schedules[position].isExpanded = true
                holder.scheduleDescription.maxLines = 999
                holder.scheduleAuthor.visibility = View.VISIBLE
            }
        }

        holder.scheduleTitle.text = schedules[position].title
        holder.scheduleDescription.text = schedules[position].description
        holder.scheduleAuthor.text = schedules[position].author
    }

}