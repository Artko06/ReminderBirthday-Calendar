package com.artkotlindev.reminderbirthday_calendar.presentation.components.evetns

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artkotlindev.reminderbirthday_calendar.LocalizedContext
import com.artkotlindev.reminderbirthday_calendar.R

@Composable
fun MonthYearText(
    modifier: Modifier = Modifier,
    numberMonth: Int,
    numberYear: Int
){
    Box(
        modifier = Modifier
            .then(modifier)
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = (LocalizedContext.current.resources.getStringArray(R.array.months)[numberMonth - 1].uppercase() +
                        " – " + numberYear.toString()),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun MonthYearTextPreview(){
    MonthYearText(
        numberMonth = 1,
        numberYear = 2026
    )
}
