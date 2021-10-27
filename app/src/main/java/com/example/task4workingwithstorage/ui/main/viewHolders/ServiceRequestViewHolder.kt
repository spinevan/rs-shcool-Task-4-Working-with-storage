package com.example.task4workingwithstorage.ui.main.viewHolders

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.example.task4workingwithstorage.databinding.ServiceRequestViewBinding
import com.example.task4workingwithstorage.interfaces.IServiceRequestEditorListener
import com.example.task4workingwithstorage.models.ServiceRequest
import com.example.task4workingwithstorage.ui.main.presentors.ServiceRequestPresenter

class ServiceRequestViewHolder(
    private val binding: ServiceRequestViewBinding,
    private val listener: IServiceRequestEditorListener,
    private val resources: Resources
) : RecyclerView.ViewHolder(binding.root) {

     fun bind(serviceRequest: ServiceRequest) {

        val serviceRequestPresenter = ServiceRequestPresenter(serviceRequest)

         with(binding) {
             name.text = serviceRequestPresenter.name
             master.text = serviceRequestPresenter.master

             if (serviceRequest.dateTime != null) {
                 dateTime.text =
                     "${serviceRequestPresenter.date} [${serviceRequestPresenter.time}]"
             }

             btnDelete.setOnClickListener {
                 listener.delete(serviceRequest)
             }

             cardBody.setOnClickListener {
                 serviceRequest.id?.let { it1 -> listener.open(it1) }
             }
         }
    }
}