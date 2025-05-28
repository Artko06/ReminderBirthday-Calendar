package com.example.reminderbirthday_calendar.presentation.components.detailWindow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.settings.ThemeType
import com.example.reminderbirthday_calendar.LocalTheme
import com.example.reminderbirthday_calendar.ui.theme.ghostlyWhite
import com.example.reminderbirthday_calendar.ui.theme.veryDarkGray

@Composable
fun DetailItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    colorIcon: Color,
    text: String,
    aboutText: String,
    onClick: (() -> Unit)? = null
){
    Card(
        modifier = Modifier.then(modifier).clickable(
            enabled = onClick != null,
            onClick = { if (onClick != null) onClick() }
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (LocalTheme.current == ThemeType.DARK) veryDarkGray else ghostlyWhite
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = colorIcon,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ){
                Text(
                    text = text,
                    fontSize = 16.sp,
                    color = if (LocalTheme.current == ThemeType.DARK) Color.White else Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = aboutText,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun DetailItem(
    modifier: Modifier = Modifier,
    painter: Painter,
    colorIcon: Color,
    text: String,
    aboutText: String
){
    Card(
        modifier = Modifier.then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = if (LocalTheme.current == ThemeType.DARK) veryDarkGray else ghostlyWhite
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ){
                Icon(
                    painter = painter,
                    contentDescription = null,
                    tint = colorIcon,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ){
                Text(
                    text = text,
                    fontSize = 16.sp,
                    color = if (LocalTheme.current == ThemeType.DARK) Color.White else Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = aboutText,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun DetailItemPreview(){
    DetailItem(
        modifier = Modifier.fillMaxWidth(),
        icon = Icons.Filled.Cake,
        colorIcon = MaterialTheme.colorScheme.primary,
        text = "1 January, 2000",
        aboutText = "Birthday"
    )
}