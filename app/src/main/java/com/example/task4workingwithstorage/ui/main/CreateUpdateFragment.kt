package com.example.task4workingwithstorage.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.task4workingwithstorage.R
import com.example.task4workingwithstorage.databinding.FragmentCreateUpdateBinding
import com.example.task4workingwithstorage.databinding.MainFragmentBinding
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*


class CreateUpdateFragment : Fragment() {

//    private var _binding: CreateUpdateFragmentBinding? = null
//    private val binding get() = _binding!!

    private var _binding: FragmentCreateUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_create_update, container, false)
        _binding = FragmentCreateUpdateBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dddd.setOnFocusChangeListener { textView, focused ->
            if (focused) {
                openDataPickerDialog()
            }
        }

        binding.dddd.setOnClickListener {
            openDataPickerDialog()
        }


    }

    fun openDataPickerDialog() {
        val now = Calendar.getInstance()
        val currentYear: Int = now.get(Calendar.YEAR)
        val currentMonth: Int = now.get(Calendar.MONTH)
        val currentDay: Int = now.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog.newInstance(DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // do something here
        }, currentYear, currentMonth, currentDay)

        datePickerDialog.setTitle("INI JUDUL")
        datePickerDialog.setAccentColor(resources.getColor(R.color.yellow_700))

        getFragmentManager()?.let { datePickerDialog.show(it, "Datepickerdialog") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(id: String?) =
            CreateUpdateFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}