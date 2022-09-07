package com.way.suitmediamobile.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.way.suitmediamobile.R
import com.way.suitmediamobile.data.remote.model.Data
import com.way.suitmediamobile.data.remote.model.UserResponse
import com.way.suitmediamobile.databinding.ItemGuestBinding
import javax.inject.Inject

class GuestAdapter @Inject constructor() :
    RecyclerView.Adapter<GuestAdapter.GuestViewHolder>() {

    private var oldGuest = emptyList<Data>()

    inner class GuestViewHolder(private val binding: ItemGuestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.ivPicture.load(data.avatar) {
                placeholder(R.drawable.ic_error_placeholder)
            }
            binding.tvPersonName.text = "${data.firstName} ${data.lastName}"
            binding.root.setOnClickListener {
                onItemClickListener?.let { it(data) }
            }
        }
    }

    override fun getItemCount(): Int = oldGuest.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val binding = ItemGuestBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GuestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        holder.bind(oldGuest[position])
    }

    fun setData(newGuest: UserResponse) {
        val diffUtil = AdapterDiffUtil(oldGuest, newGuest.data)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldGuest = newGuest.data
        diffResults.dispatchUpdatesTo(this)
    }

    private var onItemClickListener: ((Data) -> Unit)? = null
    fun setOnItemClickListener(listener: (Data) -> Unit) {
        onItemClickListener = listener
    }

}

