package com.example.spodemy.All_View.Task

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.text.TextUtilsCompat
import androidx.fragment.app.viewModels
import com.example.spodemy.R
import com.example.spodemy.Application.HMApplicaton
import com.example.spodemy.TodoDatabase.Todo
import com.example.spodemy.ViewModel.TodoModel
import com.example.spodemy.ViewModel.ViewModelFactory
import com.example.spodemy.databinding.FragmentAddTaskBinding
import kotlinx.android.synthetic.main.fragment_add_task.*
import java.util.*

@Suppress("INTEGER_OVERFLOW")
class AddTask : Fragment() ,View.OnClickListener{
private lateinit var binding: FragmentAddTaskBinding
    private val TodoViewModel: TodoModel by viewModels{
        ViewModelFactory((requireActivity().application as HMApplicaton).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddTaskBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.StartDate.setOnClickListener(this)
        binding.StartTime.setOnClickListener(this)
        binding.EndDate.setOnClickListener(this)
        binding.EndTime.setOnClickListener(this)
        binding.addplan.setOnClickListener(this)
    }
    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
       when(v!!.id){
           R.id.StartDate->{
               clickDataPicker(binding.StartDate)
               return
           }
           R.id.EndDate->{
               clickDataPicker(binding.EndDate)
               return
           }
           R.id.EndTime->{
               showTimePicker(binding.EndTime)
               return
           }
           R.id.StartTime->{
               showTimePicker(binding.StartTime)
               return
           }
           R.id.reminder->{
               reminder.setText("30 min")
           }
           R.id.addplan->{
               val plan=binding.description.text.toString().trim{it<=' '}
               val startdate=binding.StartDate.text.toString().trim{it<=' '}
               val starttime=binding.StartTime.text.toString().trim{it<=' '}
               val enddate=binding.EndDate.text.toString().trim{it<=' '}
               val endtime=binding.EndTime.text.toString().trim{it<=' '}
               val rem=binding.reminder.text.toString().trim{it<=' '}
               when{
                   plan.isEmpty() -> {
                       binding.description.error = "Required"
                       return
                   }
                   startdate.isEmpty() -> {
                       binding.StartDate.error = "Required"
                       return
                   }
                   starttime.isEmpty() -> {
                       binding.StartTime.error = "Required"
                       return
                   }
                   enddate.isEmpty() -> {
                       binding.EndDate.error = "Required"
                       return
                   }
                   endtime.isEmpty() -> {
                       binding.EndTime.error = "Required"
                       return
                   }
                   else -> {
                        val plans: Todo =Todo(
                            0,
                            plan,
                            startdate,
                            starttime,
                            enddate,
                            endtime,
                            rem
                        )
                       TodoViewModel.insertTodo(plans)
                       Toast.makeText(requireContext(), "Plan Set Successfully", Toast.LENGTH_SHORT).show()
                       binding.description.setText("")
                       binding.StartTime.setText("")
                       binding.StartDate.setText("")
                       binding.EndTime.setText("")
                       binding.EndDate.setText("")
                       binding.reminder.setText("")
                   }
               }
           }
       }
    }
    @SuppressLint("SetTextI18n")
    fun clickDataPicker(v:EditText){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                v.setText("$dayOfMonth/${monthOfYear + 1}/$year")
            },
            year,
            month,
            day
        )
//        dpd.datePicker.maxDate = Date().time + (86400000)
        dpd.show()
    }
    // function to show a time picker dialog and return the selected time as a string
    private fun showTimePicker(v:EditText) {
        val timePicker=TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            // logic to properly handle
            // the picked timings by user
            val formattedTime: String = when {
                hourOfDay == 0 -> {
                    if (minute < 10) {
                        "${hourOfDay + 12}:0${minute} am"
                    } else {
                        "${hourOfDay + 12}:${minute} am"
                    }
                }
                hourOfDay > 12 -> {
                    if (minute < 10) {
                        "${hourOfDay - 12}:0${minute} pm"
                    } else {
                        "${hourOfDay - 12}:${minute} pm"
                    }
                }
                hourOfDay == 12 -> {
                    if (minute < 10) {
                        "${hourOfDay}:0${minute} pm"
                    } else {
                        "${hourOfDay}:${minute} pm"
                    }
                }
                else -> {
                    if (minute < 10) {
                        "${hourOfDay}:${minute} am"
                    } else {
                        "${hourOfDay}:${minute} am"
                    }
                }
            }

            v.setText(formattedTime)
        }
        val calendar = Calendar.getInstance()
        // get the current hour and minute
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        // create a time picker dialog with the current time as the default value
        val timePickerDialog = TimePickerDialog(context, timePicker, hour, minute, true)
        // show the dialog
        timePickerDialog.show()
    }

}