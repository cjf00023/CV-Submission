package com.example.appassignment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class DiaryEntry : Fragment() {

    private lateinit var viewModel : MyViewModel
    private lateinit var calendarButton: Button
    private lateinit var datePicker: DatePicker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.diary_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        calendarButton = view.findViewById(R.id.calendarButton)
        datePicker = view.findViewById(R.id.datePicker)

        // Set up calendar view
        calendarButton.setOnClickListener {
            showCalendarPicker()
        }


        // Observer for the selected date
        viewModel.selectedDate.observe(viewLifecycleOwner) { selectedDate ->
            // Handle the selected date, update UI, etc.
            // Setting the Date On the DatePicker
            updateDatePicker(selectedDate)
        }
    }

    private fun showCalendarPicker() {
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()

        picker.addOnPositiveButtonClickListener { timestamp ->
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val selectedDate = sdf.format(Date(timestamp))
            viewModel.setSelectedDate(selectedDate)
        }

        picker.show(requireActivity().supportFragmentManager, picker.toString())
    }

    private fun updateDatePicker(selectedDate: String?) {
        if (!selectedDate.isNullOrBlank()) {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = sdf.parse(selectedDate)
            date?.let {
                val calendar = Calendar.getInstance()
                calendar.time = date
                datePicker.init(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    null
                )
            }
        }
    }

    // Additional method to handle saving diary entry
    // No longer needed because of the Note Fragment in the system
     /*private fun saveDiaryEntry() {
        val selectedDate = viewModel.selectedDate.value ?: ""
        val additionalNotes = notesEditText.text.toString()

        // Save the diary entry to a file using FileUtility
        if (selectedDate.isNotBlank() && additionalNotes.isNotBlank()) {
            val entryText = "$selectedDate " + ":" + "$additionalNotes\n\n"
            FileUtility.saveToFile(requireContext(), entryText)
            clearInputFields() //
            showToast("Diary entry saved!")
        } else {
            showToast("Please select a date and enter notes.")
        }
    }*/


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): DiaryEntry {
            return DiaryEntry()
        }
    }
}
