package com.way.suitmediamobile.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.way.suitmediamobile.R
import com.way.suitmediamobile.data.local.model.EventEntity
import com.way.suitmediamobile.databinding.ItemEventBinding
import javax.inject.Inject

class EventAdapter @Inject constructor() : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private var oldEvents = emptyList<EventEntity>()

    inner class EventViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity) {
            binding.ivEvent.load(event.image) {
                placeholder(R.drawable.ic_error_placeholder)
            }
            binding.tvTitle.text = event.title
            binding.tvDescription.text = event.subTitle
            binding.tvDate.text = event.eventDate
            binding.tvTime.text = event.time
            binding.root.setOnClickListener {
                onItemClickListener?.let { it(event) }
            }
        }
    }

    override fun getItemCount(): Int = oldEvents.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventAdapter.EventViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(oldEvents[position])
    }

    fun setData(newEvents: List<EventEntity>) {
        val diffUtil = AdapterDiffUtil(oldEvents, newEvents)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldEvents = newEvents
        diffResults.dispatchUpdatesTo(this)
    }

    private var onItemClickListener: ((EventEntity) -> Unit)? = null
    fun setOnItemClickListener(listener: (EventEntity) -> Unit) {
        onItemClickListener = listener
    }

}