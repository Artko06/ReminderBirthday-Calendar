package com.example.reminderbirthday_calendar.presentation.components.evetns

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchLine(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Search..."
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.PersonSearch,
                contentDescription = "Search Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 12.dp, end = 6.dp)
                    .size(22.dp)
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {onValueChange("")},
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
            ){
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        placeholder = {
            Text(
                text = placeholder,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        },
        modifier = Modifier
            .border(
                width = 2.dp,
                color = Color.DarkGray,
                shape = RoundedCornerShape(24.dp)
            )
            .fillMaxWidth()
        ,
        colors = TextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = false)
@Composable
fun SearchLinePreview() {
    SearchLine(
        value = "",
        onValueChange = {}
    )
}