package com.example.reminderbirthday_calendar.presentation.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    hasSwitch: Boolean = false,
    isSwitchChecked: Boolean = false,
    onSwitchChange: ((Boolean) -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    isLoadingStatus: Boolean = false
){
    Row(
        modifier = Modifier
            .background(color = Color.DarkGray, shape = RoundedCornerShape(percent = 35))
            .clip(shape = RoundedCornerShape(percent = 35))
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLoadingStatus){
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = Color.White
            )
        } else {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(32.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = subtitle,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = Color.LightGray
            )
        }
        if (hasSwitch) {
            Switch(
                checked = isSwitchChecked,
                onCheckedChange = onSwitchChange
            )
        } else {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = Color.White
            )
        }
    }
}

@Composable
@Preview
fun SettingsItemPreview(){
    SettingsItem(
        icon = Icons.Outlined.NotificationsActive,
        title = "Notification",
        subtitle = "Receive birthday reminders",
        hasSwitch = false,
        isSwitchChecked = true,
        onSwitchChange = {},
        onClick = {}
    )
}
