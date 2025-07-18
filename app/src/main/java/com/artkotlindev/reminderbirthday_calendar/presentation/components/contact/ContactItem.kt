package com.artkotlindev.reminderbirthday_calendar.presentation.components.contact

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.artkotlindev.domain.models.event.ContactInfo
import com.artkotlindev.reminderbirthday_calendar.LocalizedContext
import com.artkotlindev.reminderbirthday_calendar.R

@Composable
fun ContactItem(
    modifier: Modifier = Modifier,
    contact: ContactInfo,
    onSelectItem: (ContactInfo) -> Unit
) {
    Row(
        modifier = Modifier
            .then(modifier)
            .clickable(onClick = { onSelectItem(contact) }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (contact.image != null) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(32.dp))
                ) {
                    AsyncImage(
                        model = contact.image,
                        contentDescription = "Contact image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Contact",
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "${contact.name} ${contact.surname}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Text(
                    text = contact.phone.let {
                        if (it.isEmpty()) {
                            LocalizedContext.current.getString(R.string.no_number)
                        } else it
                    },
                    fontSize = 12.sp
                )
            }
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(14.dp)
        )
    }
}

@Preview(showBackground = false)
@Composable
fun ContactItemPreview() {
    ContactItem(
        modifier = Modifier.fillMaxWidth(),
        contact = ContactInfo(
            id = "1",
            name = "Elon",
            surname = "Musk",
            phone = "+375251234567",
            image = null
        ),
        onSelectItem = {}
    )
}
