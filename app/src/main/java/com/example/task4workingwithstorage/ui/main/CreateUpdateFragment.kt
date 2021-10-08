package com.example.task4workingwithstorage.ui.main

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
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
import com.example.task4workingwithstorage.viewModel.CreateUpdateViwModel
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

    val canonicalName = "com.example.task4workingwithstorage.ui.main.CreateUpdateFragment"
    private var id: Long? = null

    var createUpdateViwModel: CreateUpdateViwModel? = null

    private var _binding: FragmentCreateUpdateBinding? = null
    private val binding get() = _binding!!

    private var dateTime: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getLong(ID_SERVICE_REQUEST, -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        createUpdateViwModel = ViewModelProvider(this).get(CreateUpdateViwModel::class.java)
        id?.let { createUpdateViwModel?.loadServiceRequest(it) }
        _binding = FragmentCreateUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if ( id!! > 0 ) {
            createUpdateViwModel?.loadServiceRequest(id!!)
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

            createUpdateViwModel?.let {
                if ( it.serviceRequest != null) {
                    it.serviceRequest?.name = binding.clientName.text.toString()
                    it.serviceRequest?.master = binding.masterName.text.toString()
                    it.serviceRequest?.dateTime = dateTime.time
                    it.update(createUpdateViwModel?.serviceRequest!!)
                } else {
                    val newServiceRequst = ServiceRequest(
                        null,
                        binding.clientName.text.toString(),
                        dateTime.time,
                        binding.masterName.text.toString()
                    )
                    it.insert(newServiceRequst)
                    it.serviceRequest = newServiceRequst
                }
            }
        }

        createUpdateViwModel?.serviceRequestPresenter?.observe(viewLifecycleOwner, {
            with(binding) {
                clientName.setText(it.name)
                masterName.setText(it.master)
                dateText.setText(it.date)
                timeText.setText(it.time)
            }
        })

    }



    private fun openDataPickerDialog() {

        val currentYear: Int = dateTime.get(Calendar.YEAR)
        val currentMonth: Int = dateTime.get(Calendar.MONTH)
        val currentDay: Int = dateTime.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog.newInstance({ view, year, monthOfYear, dayOfMonth ->
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

        val timePickerDialog = TimePickerDialog.newInstance( { view, hourOfDay, minute, second ->
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