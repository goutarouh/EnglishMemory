package com.github.goutarouh.englishmemory.autoplay

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.goutarouh.englishmemory.data.sentence.Sentence
import kotlinx.coroutines.delay

@Composable
fun AutoPlayScreen(
    onAutoPlayEnd: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val stateHolder by remember { mutableStateOf(AutoPlayStateHolder(context, coroutineScope)) }
    DisposableEffect(stateHolder) {
        stateHolder.setUp()
        onDispose { stateHolder.dispose() }
    }

    val state = stateHolder.sentence.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (state.value) {
            is AutoPlayState.Loading -> {
                Loading(Modifier.align(Alignment.Center))
            }
            is AutoPlayState.Success -> {
                AutoPlayContent(state = state.value as AutoPlayState.Success) {
                    stateHolder.requestNextSentence()
                }
            }
            is AutoPlayState.End -> {
                onAutoPlayEnd()
            }
        }
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier
    )
}

@Composable
fun AutoPlayContent(
    state: AutoPlayState.Success,
    modifier: Modifier = Modifier,
    onSentenceFinish: () -> Unit
) {

    var enDisplay by remember { mutableStateOf(0f) }
    val indicator = remember { Animatable(0f) }

    LaunchedEffect(state.sentence) {
        enDisplay = 0f
        indicator.animateTo(1f, TweenSpec(durationMillis = 5000, easing = LinearEasing))
        enDisplay = 1f
        delay(3000)
        enDisplay = 0f
        indicator.animateTo(0f, TweenSpec(0))
        onSentenceFinish()
    }


    Box(modifier = modifier.fillMaxSize()) {
        Indicator(
            indicatorWidth = indicator.value,
            modifier = modifier.align(Alignment.TopCenter)
        )
        SentenceTextArea(
            sentence = state.sentence,
            modifier = Modifier.align(Alignment.Center),
            enDisplay = enDisplay
        )
    }
}

@Composable
fun Indicator(
    indicatorWidth: Float,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(40.dp)
        .padding(4.dp)
        .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
    ) {

        Layout(content = {
            Box(Modifier.background(color = Color.Blue, shape = RoundedCornerShape(8.dp)).fillMaxSize())
        }) { measurables, constraints ->

            val width = indicatorWidth * constraints.maxWidth
            val placeable = measurables.first().measure(
                constraints.copy(
                    maxWidth = width.toInt(),
                    maxHeight = constraints.maxHeight
                )
            )

            layout(constraints.maxWidth, constraints.maxHeight) {
                placeable.placeRelative(0, 0)
            }
        }
    }
}

@Composable
fun SentenceTextArea(
    sentence: Sentence,
    modifier: Modifier = Modifier,
    enDisplay: Float = 0f
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = sentence.en,
            fontSize = 48.sp,
            modifier = Modifier.graphicsLayer(alpha = enDisplay)
        )
        Text(
            text = sentence.ja,
            fontSize = 32.sp,
        )
    }
}


@Preview(
    showBackground = true,
    widthDp = 400,
)
@Composable
fun IndicatorPreview() {
    MaterialTheme {
        Indicator(indicatorWidth = 1f)
    }
}