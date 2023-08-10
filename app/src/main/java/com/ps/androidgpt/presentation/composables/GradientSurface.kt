package com.ps.androidgpt.presentation.composables

import android.content.res.Resources.Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import com.ps.androidgpt.presentation.ui.theme.DarkSurfaceEnd
import com.ps.androidgpt.presentation.ui.theme.DarkSurfaceStart

fun Modifier.gradientSurface(): Modifier = composed {
    if(isSystemInDarkTheme()){
        Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    DarkSurfaceStart,
                    DarkSurfaceEnd
                )
            )
        )
    } else {
        Modifier.background(MaterialTheme.colorScheme.surface)
    }
}