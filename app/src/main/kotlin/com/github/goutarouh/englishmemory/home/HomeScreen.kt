package com.github.goutarouh.englishmemory.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    onSettingButtonClicked: () -> Unit,
) {
    val context = LocalContext.current
    val coroutineState = rememberCoroutineScope()
    val stateHolder by remember { mutableStateOf(HomeStateHolder(context, coroutineState)) }
    DisposableEffect(stateHolder) {
        stateHolder.setUp()
        onDispose { stateHolder.dispose() }
    }

    val state = stateHolder.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        HomeContent(
            state = state.value,
            onSettingButtonClicked = onSettingButtonClicked,
        )
    }
}



@Composable
fun HomeContent(
    state: HomeState,
    onSettingButtonClicked: () -> Unit,
) {
    val density = LocalDensity.current
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val width = constraints.maxWidth
        // tablet: width: 1280  height: 727 density: 1.0
        // phone: width: 2204  height: 937 density: 2.75
        val isLarge = (width / density.density) > 1000
        SettingButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(if (isLarge) 200.dp else 48.dp),
            onSettingButtonClicked = onSettingButtonClicked
        )
        TextArea(
            state,
            modifier = Modifier.align(Alignment.Center),
            isLarge = isLarge
        )
    }
}

@Composable
fun SettingButton(
    onSettingButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onSettingButtonClicked,
        modifier = modifier.padding(top = 16.dp, start = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            modifier = Modifier.size(100.dp),
            contentDescription = null,
        )
    }
}

@Composable
fun TextArea(
    state: HomeState,
    modifier: Modifier,
    isLarge: Boolean,
) {
    val enFontSize = if (isLarge) 64.sp else 32.sp
    val jaFontSize = if (isLarge) 32.sp else 24.sp
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.phrases.isNotEmpty()) {
            val phrase = state.phrases.random()
            Text(phrase.en, fontSize = enFontSize)
            Spacer(modifier = Modifier.height(16.dp))
            Text(phrase.ja, fontSize = jaFontSize)
        }
        if (state.sentences.isNotEmpty()) {
            val sentence = state.sentences.random()
            Text(sentence.en, fontSize = enFontSize)
            Spacer(modifier = Modifier.height(16.dp))
            Text(sentence.ja, fontSize = jaFontSize)
        }
    }
}