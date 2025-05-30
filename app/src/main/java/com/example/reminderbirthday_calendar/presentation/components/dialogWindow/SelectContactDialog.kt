package com.example.reminderbirthday_calendar.presentation.components.dialogWindow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.event.ContactInfo
import com.example.reminderbirthday_calendar.LocalizedContext
import com.example.reminderbirthday_calendar.R
import com.example.reminderbirthday_calendar.presentation.components.contact.ContactItem

@Composable
fun SelectContactDialog(
    contacts: List<ContactInfo>,
    onSelectContact: (ContactInfo) -> Unit,
    onDismiss: () -> Unit
){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = LocalizedContext.current.getString(R.string.my_contact)
            )
        },
        text = {
            if (contacts.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    items(
                        items = contacts,
                        key = { it.id }
                    ) { contact ->
                        ContactItem(
                            modifier = Modifier.padding(3.dp).fillMaxWidth(),
                            contact = contact,
                            onSelectItem = onSelectContact
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.AutoStories,
                        contentDescription = "No contact icon",
                        modifier = Modifier.size(75.dp)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = LocalizedContext.current.getString(R.string.my_contact_error),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                content = {
                    Text(
                        text = LocalizedContext.current.getString(R.string.close)
                    )
                }
            )
        },
        confirmButton = {}
    )
}