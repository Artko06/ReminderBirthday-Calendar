package com.artkotlindev.reminderbirthday_calendar.presentation.components.snowflake

import androidx.annotation.IntRange
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Composable
fun Snowflake(
    modifier: Modifier = Modifier,
    @IntRange(from = 1L, to = 10L) density: Int,
    color: Color,
    contentDescription: String? = null,
) {
    val layer = remember(density, color) { SnowflakeLayer(density, color) }

    val redrawTrigger = remember { mutableStateOf(0L) }

    LaunchedEffect(layer) {
        while (true){
            withFrameMillis {
                layer.update(it)
                redrawTrigger.value++
            }
        }
    }

    Canvas(
        modifier = modifier.let {
            if (contentDescription == null)
                it
            else
                it.semantics { this.contentDescription = contentDescription }
        }
    ){
        val value = redrawTrigger.value
        layer.draw(this)
    }
}