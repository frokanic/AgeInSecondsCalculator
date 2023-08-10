package com.example.ageinsecondscalculator.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ConversionViewModel @Inject constructor() : ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    private var countdownJob: Job? = null

    var pickedDate by mutableStateOf(LocalDate.now())
    var selectedDate by mutableStateOf("")
    var secondsBetweenDates by mutableStateOf(0L)

    val formattedDate: String
        get() = DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickedDate)

    fun updatePickedDate(date: LocalDate) {
        cancelCountdown()

        pickedDate = date
        selectedDate = formattedDate

        val startDateTime = LocalDateTime.of(pickedDate, LocalTime.MIN)
        val endDateTime = LocalDateTime.now()

        secondsBetweenDates = Duration.between(startDateTime, endDateTime).seconds

        startTimer()
    }

    fun clear() {
        cancelCountdown()

        pickedDate = LocalDate.now()
        selectedDate = ""
        secondsBetweenDates = 0L
    }

    private fun startTimer() {
        countdownJob = viewModelScope.launch {
            while (secondsBetweenDates > 0) {
                delay(1000)
                secondsBetweenDates--
            }
        }
    }

    private fun cancelCountdown() {
        countdownJob?.cancel()
        countdownJob = null
    }

    override fun onCleared() {
        super.onCleared()
        cancelCountdown()
    }
}