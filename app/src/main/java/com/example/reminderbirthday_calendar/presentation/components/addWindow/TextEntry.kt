package com.example.reminderbirthday_calendar.presentation.components.addWindow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextEntry(
    description: String,
    hint: String,
    leadingIcon: ImageVector,
    textValue: String,
    onValueChanged: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = description
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp)
                .shadow(3.dp, RoundedCornerShape(25.dp)),
            value = textValue,
            onValueChange = onValueChanged,
            shape = RoundedCornerShape(15.dp),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            placeholder = {
                Text(
                    text = hint,
                    color = Color.Gray
                )
            },
            textStyle = TextStyle(
                fontSize = 14.sp
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        )

    }
}

@Preview(showBackground = true)
@Composable
fun TextEntryPreview() {
    TextEntry(
        description = "Email address",
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 0.dp, 10.dp, 5.dp)
        ,
        hint = "",
        leadingIcon = Icons.Default.Email,
        textValue = "Some text",
        onValueChanged = {}
    )
}