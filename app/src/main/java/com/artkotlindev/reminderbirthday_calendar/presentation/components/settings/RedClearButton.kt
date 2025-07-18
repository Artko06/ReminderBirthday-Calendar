package com.artkotlindev.reminderbirthday_calendar.presentation.components.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RedClearButton(
    modifier: Modifier = Modifier,
    text: String,
    onClear: () -> Unit
){
    OutlinedButton(
        onClick = onClear,
        border = BorderStroke(0.5.dp, Color.Red),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.then(modifier)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ClearAll,
                contentDescription = "Delete Icon",
                tint = Color.Red
            )

            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun RedClearButtonPreview(){
    RedClearButton(
        onClear = {},
        text = "Clear events"
    )
}
