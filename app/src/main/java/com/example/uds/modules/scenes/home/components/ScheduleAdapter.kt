package com.example.uds.modules.scenes.home.components

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.uds.R
import com.example.uds.databinding.ItemScheduleBinding
import com.example.uds.helpers.firebase_helper.FirebaseDatabaseHelper
import com.example.uds.helpers.recyclerview_helper.ItemClickListener
import com.example.uds.models.Schedule

class ScheduleAdapter(private var schedules: List<Schedule>, private var listType: String, private var context: Context) : RecyclerView.Adapter<ScheduleAdapter.ItemViewHolder> () {

    var itemClickListener: ItemClickListener? = null
    private var inflater: LayoutInflater? = null
    private var parent: ViewGroup? = null
    private val schedulesBindings : MutableList<ItemScheduleBinding> = mutableListOf()
    private val schedulesShortDescriptions: MutableList<String> = mutableListOf()

    inner class ItemViewHolder(binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        val scheduleTitle: TextView = binding.scheduleTitle
        val scheduleDescription: TextView = binding.scheduleDescription
        val scheduleAuthor: TextView = binding.scheduleAuthor
        val scheduleCard: CardView = binding.scheduleCard
        val scheduleButton: Button = binding.closeOrOpenSchedule
        val scheduleDelete: ImageView = binding.deleteSchedule

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
        schedulesBindings.add(scheduleBinding)

        return ItemViewHolder(scheduleBinding)
    }

    override fun getItemCount(): Int = schedules.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        schedulesShortDescriptions.add(schedules[position].shortDescription)

        if (listType == "Closed") {
            holder.scheduleButton.text = context.getString(R.string.reOpenSchedule)
        } else {
            holder.scheduleButton.text = context.getString(R.string.closeSchedule)
        }

        holder.scheduleDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder
                .setTitle(context.getString(R.string.deleteScheduleTitle))
                .setCancelable(true)
                .setMessage(
                    context.getString(
                        R.string.deleteScheduleMessage
                    )
                )

            builder.setPositiveButton("Sim") { dialog, which ->
                holder.scheduleCard.visibility = View.GONE
                FirebaseDatabaseHelper().removeSchedule(schedules[position])
                dialog.cancel()
            }

            builder.setNegativeButton("Não") { dialog, which ->
                dialog.cancel()
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }
        holder.scheduleCard.setOnClickListener{

            if (schedules[position].isExpanded) {
                schedules[position].isExpanded = false
                holder.scheduleDescription.maxLines = 1
                holder.scheduleDescription.text = schedules[position].shortDescription
                holder.scheduleAuthor.visibility = View.GONE
                holder.scheduleButton.visibility = View.GONE
            } else {
                var counter = 0
                for (schedule in schedulesBindings){
                    if (schedules[counter].isExpanded) {
                        schedules[counter].isExpanded = false
                        schedule.scheduleAuthor.visibility = View.GONE
                        schedule.scheduleDescription.text = schedulesShortDescriptions[counter]
                        schedule.closeOrOpenSchedule.visibility = View.GONE
                        schedule.scheduleDescription.maxLines = 1
                    }
                    counter += 1
                }
                schedules[position].isExpanded = true
                holder.scheduleDescription.maxLines = 999
                holder.scheduleDescription.text = schedules[position].fullDescription
                holder.scheduleAuthor.visibility = View.VISIBLE
                holder.scheduleButton.visibility = View.VISIBLE
            }
        }
        holder.scheduleButton.setOnClickListener {
            if (listType == "Closed") {
                FirebaseDatabaseHelper().addSchedule(schedules[position])
                holder.scheduleCard.visibility = View.GONE
            } else {
                FirebaseDatabaseHelper().closeSchedule(schedules[position])
                holder.scheduleCard.visibility = View.GONE
            }
        }
        holder.scheduleTitle.text = schedules[position].title
        holder.scheduleDescription.text = schedules[position].shortDescription
        holder.scheduleAuthor.text = schedules[position].author
    }

}