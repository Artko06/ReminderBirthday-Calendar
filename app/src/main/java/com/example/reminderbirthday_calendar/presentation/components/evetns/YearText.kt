package com.example.reminderbirthday_calendar.presentation.components.evetns

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun YearText(
    numberYear: Int
){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
       Text(
           text = numberYear.toString(),
           fontSize = 20.sp,
           fontWeight = FontWeight.Bold,
           fontFamily = FontFamily.Serif,
           color = MaterialTheme.colorScheme.primary
       )
    }
}

@Preview(showBackground = false)
@Composable
fun YearTextPreview(){
    YearText(
        numberYear = 2026
    )
}