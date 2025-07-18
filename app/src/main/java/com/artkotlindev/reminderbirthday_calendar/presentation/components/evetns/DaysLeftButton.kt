package com.artkotlindev.reminderbirthday_calendar.presentation.components.evetns

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DaysLeftButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    isActive: Boolean
){
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(1.dp, if (isActive) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onBackground),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.then(modifier)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Timer,
                contentDescription = "Left days Icon",
                tint = if (isActive) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isActive) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun DaysLeftButtonPreview(){
    DaysLeftButton(
        text = "View days left",
        onClick = {},
        isActive = false
    )
}