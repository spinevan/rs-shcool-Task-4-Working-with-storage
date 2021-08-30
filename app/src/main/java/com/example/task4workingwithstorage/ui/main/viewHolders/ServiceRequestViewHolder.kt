package com.example.task4workingwithstorage.ui.main.viewHolders

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.example.task4workingwithstorage.databinding.ServiceRequestViewBinding
import com.example.task4workingwithstorage.interfaces.IServiceRequestListener
import com.example.task4workingwithstorage.models.ServiceRequest

class ServiceRequestViewHolder(
    private val binding: ServiceRequestViewBinding,
    private val listener: IServiceRequestListener,
    private val resources: Resources
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(serviceRequest: ServiceRequest) {

        binding.id.text = serviceRequest.id.toString()
        binding.name.text = serviceRequest.name

    }





    private companion object {

        private const val START_TIME = "00:00:00"

    }
}