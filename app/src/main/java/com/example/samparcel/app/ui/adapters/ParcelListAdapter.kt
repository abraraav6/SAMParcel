package com.samparcel.app.ui.adapters

import android.os.Parcel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.samparcel.app.databinding.ItemParcelBinding
import java.time.format.DateTimeFormatter

class ParcelListAdapter(
    private val onItemClick: (Parcel) -> Unit
) : ListAdapter<Parcel, ParcelListAdapter.ParcelViewHolder>(ParcelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParcelViewHolder {
        val binding = ItemParcelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ParcelViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ParcelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ParcelViewHolder(
        private val binding: ItemParcelBinding,
        private val onItemClick: (Parcel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(parcel: Parcel) {
            binding.apply {
                tvTrackingNumber.text = parcel.trackingNumber
                tvStatus.text = parcel.status.toString()
                tvWeight.text = "${parcel.weight} kg"
                tvCreatedAt.text = parcel.createdAt.format(
                    DateTimeFormatter.ofPattern("dd MMM yyyy")
                )

                root.setOnClickListener { onItemClick(parcel) }
            }
        }
    }

    class ParcelDiffCallback : DiffUtil.ItemCallback<Parcel>() {
        override fun areItemsTheSame(oldItem: Parcel, newItem: Parcel): Boolean {
            return oldItem.trackingNumber == newItem.trackingNumber
        }

        override fun areContentsTheSame(oldItem: Parcel, newItem: Parcel): Boolean {
            return oldItem == newItem
        }
    }
}