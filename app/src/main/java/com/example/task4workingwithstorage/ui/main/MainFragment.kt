package com.example.task4workingwithstorage.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task4workingwithstorage.R
import com.example.task4workingwithstorage.databinding.MainFragmentBinding
import com.example.task4workingwithstorage.interfaces.IMainActivityNav
import com.example.task4workingwithstorage.interfaces.IServiceRequestListener
import com.example.task4workingwithstorage.models.ServiceRequest
import com.example.task4workingwithstorage.ui.main.adapters.RecyclerViewAdapter
import com.example.task4workingwithstorage.viewModel.ServiceRequestViewModel

import java.util.*

class MainFragment : Fragment(), IServiceRequestListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var mainActivityListener: IMainActivityNav? = null

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val recyclerViewAdapter = RecyclerViewAdapter(this)
    private var serviceRequestViewModel: ServiceRequestViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityListener = context as IMainActivityNav
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        serviceRequestViewModel = ViewModelProvider(this).get(ServiceRequestViewModel::class.java)

        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

        //return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.serviceRequestRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }

        serviceRequestViewModel?.allServiceRequests?.observe(viewLifecycleOwner, Observer{ serviceRequests->
            // Data bind the recycler view
            recyclerViewAdapter.submitList(serviceRequests)
            println(serviceRequests.size)
        })

        binding.floatingActionAdd.setOnClickListener {
            //serviceRequestViewModel?.insert(ServiceRequest(null, "Иван Владимирович С.", Date(), "Алексей Иванов" ))
            mainActivityListener?.openCreateFragment()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun delete(serviceRequest: ServiceRequest) {
        serviceRequestViewModel?.delete(serviceRequest)
    }


}