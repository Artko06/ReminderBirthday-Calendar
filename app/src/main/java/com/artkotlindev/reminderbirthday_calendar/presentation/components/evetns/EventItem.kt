package com.artkotlindev.reminderbirthday_calendar.presentation.components.evetns

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.artkotlindev.domain.models.event.EventType
import com.artkotlindev.domain.models.event.SortTypeEvent
import com.artkotlindev.domain.models.settings.ThemeType
import com.artkotlindev.reminderbirthday_calendar.LocalTheme
import com.artkotlindev.reminderbirthday_calendar.LocalizedContext
import com.artkotlindev.reminderbirthday_calendar.R
import com.artkotlindev.reminderbirthday_calendar.ui.theme.blueAzure
import com.artkotlindev.reminderbirthday_calendar.ui.theme.darkGreen
import com.artkotlindev.reminderbirthday_calendar.ui.theme.darkPurple
import com.artkotlindev.reminderbirthday_calendar.ui.theme.darkRed
import com.artkotlindev.reminderbirthday_calendar.ui.theme.platinum
import com.artkotlindev.reminderbirthday_calendar.ui.theme.yellow

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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(shape = RoundedCornerShape(24.dp))
            .clickable(
                onClick = { onNavigateByClick(id) }
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (LocalTheme.current == ThemeType.DARK) Color.DarkGray else platinum
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(
                        color = when (sortTypeEvent) {
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

                if (image != null) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(46.dp))
                    ) {
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
                        fontSize = 18.sp,
                        lineHeight = 19.sp,
                        color = if (LocalTheme.current == ThemeType.DARK) Color.White else Color.Black
                    )

                    Text(
                        text = (if (yearMatter) date else date.replaceRange(6, 10, "????")) + " — " +
                                when(eventType){
                                    EventType.BIRTHDAY -> LocalizedContext.current.getString(R.string.birthday)
                                    EventType.ANNIVERSARY -> LocalizedContext.current.getString(R.string.anniversary)
                                    EventType.OTHER -> LocalizedContext.current.getString(R.string.event)
                                },
                        fontWeight = FontWeight.Light,
                        fontSize = 11.sp,
                        lineHeight = 12.sp,
                        color = if (LocalTheme.current == ThemeType.DARK) Color.LightGray else Color.DarkGray
                    )
                }

                if (daysLeft < 0){
                    Column(
                        verticalArrangement = Arrangement.spacedBy(1.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = (daysLeft * -1).toString(),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            lineHeight = 14.sp,
                            color = Color.Gray
                        )

                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(LocalizedContext.current.resources
                                        .getQuantityString(R.plurals.days, daysLeft * (-1), daysLeft * (-1))
                                    )
                                }

                                append(" " + LocalizedContext.current.getString(R.string.ago))
                            },
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            lineHeight = 14.sp,
                            color = Color.Gray
                        )
                    }
                } else if (daysLeft == 0) {
                    Box(
                        modifier = Modifier.padding(end = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Cake,
                            contentDescription = null,
                            tint = when (sortTypeEvent) {
                                SortTypeEvent.FAMILY -> darkRed
                                SortTypeEvent.RELATIVE -> yellow
                                SortTypeEvent.FRIEND -> blueAzure
                                SortTypeEvent.COLLEAGUE -> darkGreen
                                SortTypeEvent.OTHER -> darkPurple
                            }
                        )
                    }
                } else if (isViewDaysLeft) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(1.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = daysLeft.toString(),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            lineHeight = 14.sp,
                            color = Color.Gray
                        )

                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(LocalizedContext.current.resources
                                        .getQuantityString(R.plurals.days, daysLeft, daysLeft)
                                    )
                                }

                                append(" " + LocalizedContext.current.getString(R.string.left))
                            },
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            lineHeight = 14.sp,
                            color = Color.Gray
                        )
                    }
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(1.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = LocalizedContext.current.getString(R.string.turns).replaceFirstChar { it.uppercase() },
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            lineHeight = 14.sp,
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
                                        append(if (yearMatter) age.toString() else "?")
                                    }

                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.Light
                                        )
                                    ) {
                                        if (yearMatter){
                                            append(" " + LocalizedContext.current.resources
                                                .getQuantityString(R.plurals.years, age, age))
                                        } else{
                                            append(" " + LocalizedContext.current.resources
                                                .getQuantityString(R.plurals.years, 99, 99))
                                        }
                                    }
                                },
                                textAlign = TextAlign.Center,
                                fontSize = 13.sp,
                                lineHeight = 14.sp,
                                color = Color.Gray
                            )
                        }
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