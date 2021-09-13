package com.example.task4workingwithstorage.ui.main

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.preference.PreferenceManager
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
import kotlinx.coroutines.*

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

    val uiScope = CoroutineScope(Dispatchers.Main)
    val bgDispatcher: CoroutineDispatcher = Dispatchers.IO

    private fun loadData() = uiScope.launch {
        //showLoading // ui thread
        val updatedServiceRequest = withContext(bgDispatcher) { // background thread
            return@withContext serviceRequestViewModel?.getAll()
        }
        updatedServiceRequest?.observe(viewLifecycleOwner, { serviceRequests->
            recyclerViewAdapter.submitList(serviceRequests)
        })
    }

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

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.serviceRequestRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }

        serviceRequestViewModel?.changeDAO()
        serviceRequestViewModel?.allServiceRequests?.observe(viewLifecycleOwner, { serviceRequests->
            recyclerViewAdapter.submitList(serviceRequests)
        })

        binding.floatingActionAdd.setOnClickListener {
            mainActivityListener?.openCreateFragment()
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.preferences -> {
                    // Handle favorite icon press
                    mainActivityListener?.openPreferencesFragment()
                    true
                }
                else -> false
            }
        }

    }

    override fun onResume() {
        super.onResume()

        val sharedPrefs =  PreferenceManager.getDefaultSharedPreferences(context)
        val useCursor = sharedPrefs.getBoolean("useCursor", false)

        if (useCursor != serviceRequestViewModel?.useCursor ) {
            serviceRequestViewModel?.useCursor = useCursor
            serviceRequestViewModel?.changeDAO()
        }

        loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun delete(serviceRequest: ServiceRequest) {
        serviceRequestViewModel?.delete(serviceRequest)
        loadData() //for cursor
    }

    override fun open(id: Long) {
        mainActivityListener?.openUpdateFragment(id)
    }


}