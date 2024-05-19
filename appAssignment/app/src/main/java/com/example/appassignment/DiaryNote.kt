package com.example.appassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class DiaryNote : Fragment() {

    private lateinit var viewModel: MyViewModel
    private lateinit var notesEditText: EditText
    private lateinit var saveNoteButton: Button
    private lateinit var clearButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.diary_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        notesEditText = view.findViewById(R.id.diaryNoteEditText)
        saveNoteButton = view.findViewById(R.id.saveNoteButton)
        clearButton = view.findViewById(R.id.clearNoteButton)

        clearButton.setOnClickListener {
            clearInputFields()
        }

        saveNoteButton.setOnClickListener {
            saveDiaryNote()
        }
    }

    private fun saveDiaryNote() {
        val selectedDate = viewModel.selectedDate.value ?: ""
        val diaryNote = notesEditText.text.toString()

        // Save the diary note to a file using FileUtility
        if (selectedDate.isNotBlank() && diaryNote.isNotBlank()) {
            val noteText = "$selectedDate " + ":" + "$diaryNote\n"
            FileUtility.saveToFile(requireContext(), noteText)
            showToast("Diary note saved!")
            clearInputFields()
        } else {
            showToast("Please enter a diary note.")
        }
    }
    private fun showToast(message: String) {
        // Display a Toast message
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun clearInputFields() {
        notesEditText.text.clear()
    }

    companion object {
        fun newInstance(): DiaryNote {
            return DiaryNote()
        }
    }
}
