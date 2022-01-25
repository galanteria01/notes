package com.shanu.notes_app.ui.splash_screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shanu.notes_app.R
import com.shanu.notes_app.util.Routes
import com.shanu.notes_app.util.UiEvent
import kotlinx.coroutines.delay

@Composable
fun Splash(
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }

            )
        )
        delay(1000L)
        onNavigate(UiEvent.Navigate(Routes.NOTE_LIST))
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_notes_icon),
            contentDescription = "Notes Icon",
            modifier = Modifier.size(120.dp)
        )
    }

}