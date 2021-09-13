package com.example.task4workingwithstorage.ui.main

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.task4workingwithstorage.R
import com.example.task4workingwithstorage.databinding.FragmentCreateUpdateBinding
import com.example.task4workingwithstorage.databinding.MainFragmentBinding
import com.example.task4workingwithstorage.models.ServiceRequest
import com.example.task4workingwithstorage.viewModel.ServiceRequestViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.coroutines.*
import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.util.*


class CreateUpdateFragment : Fragment() {

//    private var _binding: CreateUpdateFragmentBinding? = null
//    private val binding get() = _binding!!
    private var id: Long? = null

    private var _binding: FragmentCreateUpdateBinding? = null
    private val binding get() = _binding!!

    private var serviceRequestViewModel: ServiceRequestViewModel? = null
    var serviceRequest: ServiceRequest? = null

    private var dateTime: Calendar = Calendar.getInstance()
    val uiScope = CoroutineScope(Dispatchers.Main)
    val bgDispatcher: CoroutineDispatcher = Dispatchers.IO

    private fun loadData() = uiScope.launch {
        //showLoading // ui thread
        serviceRequest = withContext(bgDispatcher) { // background thread
            return@withContext serviceRequestViewModel?.getById(id!!)
        }
        // ui thread
        serviceRequest?.let {
            binding.clientName.setText( it.name )
            binding.masterName.setText( it.master )
            val calendar = Calendar.getInstance()
            if (it.dateTime != null) {
                calendar.time = it.dateTime
            }
            dateTime = calendar
            setTextDate()
            setTextTime()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getLong(ID_SERVICE_REQUEST, -1)
        }

        if ( id!! > 0 ) {
            println("это ид записи")
            println(id)

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_create_update, container, false)
        serviceRequestViewModel = ViewModelProvider(this).get(ServiceRequestViewModel::class.java)

        _binding = FragmentCreateUpdateBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if ( id!! > 0 ) {
            loadData()
            binding.textTitle.text = "Редактирование записи на сервис"
        }

        binding.dateText.setOnFocusChangeListener { textView, focused ->
            if (focused) {
                openDataPickerDialog()
            }
        }

        binding.dateText.setOnClickListener {
            openDataPickerDialog()
        }

        binding.timeText.setOnFocusChangeListener { textView, focused ->
            if (focused) {
                openTimePickerDialog()
            }
        }

        binding.timeText.setOnClickListener {
            openTimePickerDialog()
        }

        binding.btnAdd.setOnClickListener {
            if ( serviceRequest != null) {
                serviceRequest?.name = binding.clientName.text.toString()
                serviceRequest?.master = binding.masterName.text.toString()
                serviceRequest?.dateTime = dateTime.time
                serviceRequestViewModel?.update(serviceRequest!!)
            } else {
                val newServiceRequst = ServiceRequest(
                    null,
                    binding.clientName.text.toString(),
                    dateTime.time,
                    binding.masterName.text.toString()
                )
                serviceRequestViewModel?.insert(newServiceRequst)
                serviceRequest = newServiceRequst
            }

        }

        setTextDate()
        setTextTime()

    }


    private fun openDataPickerDialog() {

        val currentYear: Int = dateTime.get(Calendar.YEAR)
        val currentMonth: Int = dateTime.get(Calendar.MONTH)
        val currentDay: Int = dateTime.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog.newInstance(DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            calendar.set(Calendar.HOUR_OF_DAY, dateTime.get(Calendar.HOUR_OF_DAY))
            calendar.set(Calendar.MINUTE, dateTime.get(Calendar.MINUTE))

            dateTime = calendar
            setTextDate()

        }, currentYear, currentMonth, currentDay)

        datePickerDialog.accentColor = resources.getColor(R.color.yellow_700)

        getFragmentManager()?.let { datePickerDialog.show(it, "Datepickerdialog") }
    }

    private fun openTimePickerDialog() {

        val timePickerDialog = TimePickerDialog.newInstance(TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute, second ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, dateTime.get(Calendar.YEAR) )
            calendar.set(Calendar.MONTH, dateTime.get(Calendar.MONTH))
            calendar.set(Calendar.DAY_OF_MONTH, dateTime.get(Calendar.DAY_OF_MONTH))
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)

            dateTime = calendar
            setTextTime()

        }, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true)

        timePickerDialog.accentColor = resources.getColor(R.color.yellow_700)

        getFragmentManager()?.let { timePickerDialog.show(it, "TimePickerDialog") }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setTextDate() {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        binding.dateText.setText( formatter.format(dateTime.timeInMillis) )
    }

    private fun setTextTime() {
        val formatter = SimpleDateFormat("HH-mm")
        binding.timeText.setText( formatter.format(dateTime.timeInMillis) )
    }

    companion object {

        @JvmStatic
        fun newInstance(id: Long?) =
            CreateUpdateFragment().apply {
                arguments = Bundle().apply {
                    if ( id != null )
                        putLong(ID_SERVICE_REQUEST, id)
                }
            }
        private const val ID_SERVICE_REQUEST = "ID_SERVICE_REQUEST"
    }
}