package com.example.appassignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String>
        get() = _selectedDate

    private val _additionalNotes = MutableLiveData<String>()
    val additionalNotes: LiveData<String>
        get() = _additionalNotes

    init {
        _selectedDate.value = ""
        _additionalNotes.value = ""
    }

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun setAdditionalNotes(notes: String) {
        _additionalNotes.value = notes
    }

}
