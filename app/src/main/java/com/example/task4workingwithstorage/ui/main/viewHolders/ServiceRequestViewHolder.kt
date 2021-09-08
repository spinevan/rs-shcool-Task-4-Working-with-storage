package com.example.task4workingwithstorage.ui.main.viewHolders

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.example.task4workingwithstorage.databinding.ServiceRequestViewBinding
import com.example.task4workingwithstorage.interfaces.IServiceRequestListener
import com.example.task4workingwithstorage.models.ServiceRequest
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ServiceRequestViewHolder(
    private val binding: ServiceRequestViewBinding,
    private val listener: IServiceRequestListener,
    private val resources: Resources
) : RecyclerView.ViewHolder(binding.root) {

    private val formatter = SimpleDateFormat("yyyy-MM-dd [HH:mm]", Locale.forLanguageTag("RU"))

    fun bind(serviceRequest: ServiceRequest) {

        binding.name.text = serviceRequest.name
        if (serviceRequest.dateTime != null) {
            binding.dateTime.text = formatter.format(serviceRequest.dateTime as Date)
        }
        binding.master.text = serviceRequest.master

        binding.btnDelete.setOnClickListener {
            listener.delete(serviceRequest)
        }

        binding.cardBody.setOnClickListener {
            serviceRequest.id?.let { it1 -> listener.open(it1) }
        }

    }


    private companion object {

    }
}