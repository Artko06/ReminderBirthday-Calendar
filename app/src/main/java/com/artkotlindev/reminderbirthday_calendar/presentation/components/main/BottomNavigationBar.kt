package com.artkotlindev.reminderbirthday_calendar.presentation.components.main

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.artkotlindev.reminderbirthday_calendar.presentation.navigation.model.BottomNavigationItem

@Composable
fun BottomNavigationBar(
    items: List<BottomNavigationItem>,
    modifier: Modifier = Modifier,
    selectedNavItem: Int,
    onSelectNavItem: (Int) -> Unit
) {
    NavigationBar(
        modifier = modifier
    ) {
        items.forEachIndexed { index, navItem ->
            NavigationBarItem(
                selected = selectedNavItem == index ,
                onClick = {
                    onSelectNavItem(index)
                },
                icon = {
                    Icon(
                        imageVector = if (selectedNavItem == index) navItem.selectedIcon
                            else navItem.unselectedIcon,
                        contentDescription = navItem.title
                    )
                },
                label = {
                    Text(
                        text = navItem.title,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(lineHeight = 12.sp)
                    )
                }
            )
        }
    }
}