package com.example.ageinsecondscalculator.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import androidx.hilt.navigation.compose.hiltViewModel
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Preview(showBackground = true)
@Composable
fun AgeInSeconds(viewModel: ConversionViewModel = hiltViewModel()) {
    val pickedDate = viewModel.pickedDate
    val selectedDate = viewModel.selectedDate
    val seconds = viewModel.secondsBetweenDates.toString()

    val dateDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok")
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = pickedDate
        ) { date ->
            viewModel.updatePickedDate(date)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Buttons("Select Date") { dateDialogState.show() }
        SelectedDate(selectedDate)
        Seconds(seconds)
        Buttons("Clear") { viewModel.clear() }
    }
}

@Composable
fun Seconds(
    seconds: String = "0"
) {
    Text(
        text = seconds,
        fontSize = 30.sp,
        color = Color(0xFF3A77D4),
        fontFamily = FontFamily.Serif,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SelectedDate(
    selectedDate: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Selected Date: ",
            color = Color(0xFF3A77D4),
            fontSize = 18.sp
        )
        Text(
            text = selectedDate,
            color = Color(0xFF3A77D4),
            fontSize = 18.sp,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun Buttons(
    buttonText: String,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = {
            println("Button clicked")
            onClick()
        },
        colors = ButtonDefaults
            .buttonColors(
                containerColor = Color(0xFF3A77D4)
            ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = buttonText
        )
    }
}