package com.samparcel.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.samparcel.app.databinding.ItemAddressBinding
import com.example.samparcel.app.models.Address

class AddressAdapter(
    private val onAddressSelect: (Address) -> Unit
) : ListAdapter<Address, AddressAdapter.AddressViewHolder>(AddressDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = ItemAddressBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AddressViewHolder(binding, onAddressSelect)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AddressViewHolder(
        private val binding: ItemAddressBinding,
        private val onAddressSelect: (Address) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(address: Address) {
            binding.apply {
                tvStreet.text = address.street
                tvCityStatePostal.text = "${address.city}, ${address.state} ${address.postalCode}"
                tvCountry.text = address.country

                root.setOnClickListener { onAddressSelect(address) }
            }
        }
    }

    class AddressDiffCallback : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem.street == newItem.street &&
                    oldItem.city == newItem.city &&
                    oldItem.postalCode == newItem.postalCode
        }

        override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem == newItem
        }
    }
}