package com.github.goutarouh.englishmemory.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val stateHolder by remember {
        mutableStateOf(SettingStateHolder(context, coroutineScope))
    }
    DisposableEffect(key1 = stateHolder) {
        onDispose { stateHolder.dispose() }
    }

    val state = stateHolder.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                stateHolder.fetchAllInformation()
            },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = "UPDATE",
                fontSize = 108.sp
            )
        }
        if (state.value) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(300.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}