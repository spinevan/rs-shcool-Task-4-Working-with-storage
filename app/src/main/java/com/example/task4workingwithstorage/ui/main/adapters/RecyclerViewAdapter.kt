package com.example.task4workingwithstorage.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task4workingwithstorage.R
import com.example.task4workingwithstorage.databinding.ServiceRequestViewBinding
import com.example.task4workingwithstorage.interfaces.IServiceRequestListener
import com.example.task4workingwithstorage.models.ServiceRequest
import com.example.task4workingwithstorage.ui.main.viewHolders.ServiceRequestViewHolder

class RecyclerViewAdapter(
    private val listener: IServiceRequestListener
) : ListAdapter<ServiceRequest, ServiceRequestViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceRequestViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ServiceRequestViewBinding.inflate(layoutInflater, parent, false)
        return ServiceRequestViewHolder(binding, listener, binding.root.context.resources)
    }

    override fun onBindViewHolder(holder: ServiceRequestViewHolder, position: Int) {
        //holder.bind(getItem(position))
        val current: ServiceRequest = getItem(position)
        holder.bind(current)
    }

    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<ServiceRequest>() {

            override fun areItemsTheSame(oldItem: ServiceRequest, newItem: ServiceRequest): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ServiceRequest, newItem: ServiceRequest): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.name == newItem.name  &&
                        oldItem.dateTime == newItem.dateTime
            }

            override fun getChangePayload(oldItem: ServiceRequest, newItem: ServiceRequest) = Any()
        }
    }
}