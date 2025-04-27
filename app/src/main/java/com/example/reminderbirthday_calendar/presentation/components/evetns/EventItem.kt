package com.example.reminderbirthday_calendar.presentation.components.evetns

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.event.EventType

@Composable
fun EventItem(
    name: String,
    surname: String,
    date: String,
    eventType: EventType,
    age: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(24.dp))
            .background(Color.DarkGray)
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(Color.Yellow, shape = RoundedCornerShape(24.dp))
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(14.dp)
        ) {

            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Profile",
                modifier = Modifier.size(48.dp),
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = "$name $surname",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )

                Row {
                    Text(
                        text = date + " — ${
                            eventType.name.lowercase().replaceFirstChar { it.uppercase() }
                        }",
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Turns",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = Color.Gray
                )
                Row {
                    Text(
                        text = age.toString(),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = " years",
                        fontWeight = FontWeight.Light,
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = false)
@Composable
fun EventItemPreview() {
    EventItem(
        name = "Артём",
        surname = "Кохан",
        date = "03.01.2006",
        eventType = EventType.BIRTHDAY,
        age = 20
    )
}