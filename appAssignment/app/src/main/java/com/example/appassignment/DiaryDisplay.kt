package com.example.appassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment

class DiaryDisplay : Fragment() {

    private lateinit var diaryListView: ListView
    private lateinit var clearButton: Button
    private lateinit var deleteButton: Button
    private lateinit var updateButton: Button
    private lateinit var searchButton: Button
    private lateinit var searchEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.diary_display, container, false)

        diaryListView = view.findViewById(R.id.diaryListView)
        clearButton = view.findViewById(R.id.clearButton)
        deleteButton = view.findViewById(R.id.deleteButton)
        updateButton = view.findViewById(R.id.updateButton)
        searchButton = view.findViewById(R.id.searchButton)  // Add this line
        searchEditText = view.findViewById(R.id.searchEditText)

        // Set up click listeners
        clearButton.setOnClickListener { clearDiaryEntries() }
        deleteButton.setOnClickListener { deleteNotesForSelectedDate() }
        updateButton.setOnClickListener { displayDiaryEntries() }
        searchButton.setOnClickListener { displaySelectedDiaryEntries() }  // Fix the button reference
        return view
    }


    private fun clearDiaryEntries() {
        FileUtility.clearFile(requireContext())
        displayDiaryEntries() // Refresh the display after clearing
    }

    private fun deleteNotesForSelectedDate() {
        val selectedDate = searchEditText.text.toString()
        if (selectedDate.isNotBlank()) {
            FileUtility.deleteNotesForDate(requireContext(), selectedDate)
            showToast("Notes for $selectedDate deleted!")
            displayDiaryEntries() // Refresh the display after deletion
        } else {
            showToast("Please enter a date to delete notes.")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun displayDiaryEntries() {
        val entries = FileUtility.readFromFile(requireContext())
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, entries)
        diaryListView.adapter = adapter
    }
    private fun displaySelectedDiaryEntries() {
        val searchText = searchEditText.text.toString().trim()
        val filteredEntries = if (searchText.isNotEmpty()) {
            // If a search date is entered, use the searchByDate method
            val entries = FileUtility.searchByDate(requireContext(), searchText)
            if (entries.isEmpty()) {
                showToast("No entries found for $searchText")
            }
            entries
        } else {
            // If no search date is entered, show all entries
            FileUtility.readFromFile(requireContext())
        }

        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, filteredEntries)
        diaryListView.adapter = adapter
    }



    companion object {
        fun newInstance(): DiaryDisplay {
            return DiaryDisplay()
        }
    }
}

