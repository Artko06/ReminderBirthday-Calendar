package com.example.reminderbirthday_calendar.presentation.components.evetns

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.models.event.EventType
import com.example.domain.models.event.SortTypeEvent
import com.example.domain.models.settings.ThemeType
import com.example.reminderbirthday_calendar.LocalTheme
import com.example.reminderbirthday_calendar.ui.theme.blueAzure
import com.example.reminderbirthday_calendar.ui.theme.darkGreen
import com.example.reminderbirthday_calendar.ui.theme.darkPurple
import com.example.reminderbirthday_calendar.ui.theme.darkRed
import com.example.reminderbirthday_calendar.ui.theme.platinum
import com.example.reminderbirthday_calendar.ui.theme.yellow

@Composable
fun EventItem(
    id: Long,
    name: String,
    surname: String,
    date: String,
    yearMatter: Boolean,
    eventType: EventType,
    sortTypeEvent: SortTypeEvent,
    age: Int,
    isViewDaysLeft: Boolean,
    daysLeft: Int,
    image: ByteArray?,
    onNavigateByClick: (Long) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(24.dp))
            .background(
                color = if (LocalTheme.current == ThemeType.DARK) Color.DarkGray else platinum,
                shape = RoundedCornerShape(24.dp)
            )
            .height(IntrinsicSize.Min)
            .clickable(onClick = { onNavigateByClick(id) })
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(
                    color = when(sortTypeEvent){
                        SortTypeEvent.FAMILY -> darkRed
                        SortTypeEvent.RELATIVE -> yellow
                        SortTypeEvent.FRIEND -> blueAzure
                        SortTypeEvent.COLLEAGUE -> darkGreen
                        SortTypeEvent.OTHER -> darkPurple
                    },
                    shape = RoundedCornerShape(24.dp)
                )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {

            if(image != null){
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(46.dp))
                ){
                    AsyncImage(
                        model = image,
                        contentDescription = "User image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier.size(46.dp),
                    tint = if (LocalTheme.current == ThemeType.DARK) Color.White else Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "$name $surname",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    lineHeight = 21.sp,
                    color = if (LocalTheme.current == ThemeType.DARK) Color.White else Color.Black
                )

                Text(
                    text = (if (yearMatter) date else date.replaceRange(6, 10, "????")) + " — ${
                        eventType.name.lowercase().replaceFirstChar { it.uppercase() }
                    }",
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    lineHeight = 13.sp,
                    color = if (LocalTheme.current == ThemeType.DARK) Color.LightGray else Color.DarkGray
                )
            }

            if (daysLeft == 0){
                Box(
                    modifier = Modifier.padding(end = 8.dp),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Filled.Cake,
                        contentDescription = null,
                        tint = when(sortTypeEvent){
                            SortTypeEvent.FAMILY -> darkRed
                            SortTypeEvent.RELATIVE -> yellow
                            SortTypeEvent.FRIEND -> blueAzure
                            SortTypeEvent.COLLEAGUE -> darkGreen
                            SortTypeEvent.OTHER -> darkPurple
                        }
                    )
                }
            }
            else if (isViewDaysLeft){
                Column(
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = daysLeft.toString(),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("days")
                            }

                            append(" left")
                        },
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        lineHeight = 17.sp,
                        color = Color.Gray
                    )
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Turns",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    
                    Row {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold,

                                        )
                                ) {
                                    append( if(yearMatter) age.toString() else "?" )
                                }

                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Light
                                    )
                                ) {
                                    append(" years")
                                }
                            },
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            lineHeight = 17.sp,
                            color = Color.Gray
                        )
                    }
                }
            }


        }
    }
}

@Preview(showSystemUi = false)
@Composable
fun EventItemPreview() {
    EventItem(
        id = 0,
        name = "Elon",
        surname = "Musk",
        date = "28.06.1971",
        yearMatter = true,
        eventType = EventType.BIRTHDAY,
        sortTypeEvent = SortTypeEvent.RELATIVE,
        age = 54,
        isViewDaysLeft = true,
        daysLeft = 365,
        image = null,
        onNavigateByClick = {}
    )
}