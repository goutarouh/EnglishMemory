package com.github.goutarouh.englishmemory.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onAutoPlayButtonClicked: () -> Unit
) {
    val context = LocalContext.current
    val coroutineState = rememberCoroutineScope()
    val stateHolder by remember { mutableStateOf(HomeStateHolder(context, coroutineState)) }
    DisposableEffect(stateHolder) {
        stateHolder.setUp()
        onDispose { stateHolder.dispose() }
    }

    val state = stateHolder.homeState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (state.value) {
            is HomeState.Loading -> {
                Loading(Modifier.align(Alignment.Center))
            }
            is HomeState.Success -> {
                HomeContent(
                    state = state.value as HomeState.Success,
                    onSentencesUpdateButtonClicked = {
                        stateHolder.updateSentences()
                    },
                    onAutoPlayButtonClicked = onAutoPlayButtonClicked
                )
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
fun HomeContent(
    state: HomeState.Success,
    onSentencesUpdateButtonClicked: () -> Unit,
    onAutoPlayButtonClicked: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AutoPlayStartButton(
            onAutoPlayButtonClicked = onAutoPlayButtonClicked,
            modifier = Modifier.align(Alignment.Center)
        )
        CurrentSentencesStatus(
            currentRegisteredSentencesNum = state.currentRegisteredSentencesNum,
            lastUpdated = state.lastUpdated,
            modifier = Modifier.align(Alignment.TopEnd)
        )
        SentenceUpdateButton(
            onSentencesUpdateButtonClicked = onSentencesUpdateButtonClicked,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun AutoPlayStartButton(
    onAutoPlayButtonClicked: () -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = onAutoPlayButtonClicked,
        modifier = modifier
            .background(shape = RoundedCornerShape(10.dp), color = MaterialTheme.colors.primary)
    ) {
        Text(
            text = "Learn English",
            modifier = Modifier.padding(64.dp),
            fontSize = 108.sp
        )
    }
}

@Composable
fun CurrentSentencesStatus(
    currentRegisteredSentencesNum: Int,
    lastUpdated: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier.padding(32.dp)
    ) {
        Row {
            Text(
                text = "現在の文章の数:  ",
                fontSize = 32.sp
            )
            Text(
                text = "${currentRegisteredSentencesNum}",
                fontSize = 32.sp
            )
        }
        Row {
           Text(
               text = "最終更新日時:  ",
               fontSize = 32.sp
           )
           Text(
               text = lastUpdated,
               fontSize = 32.sp
           )
        }
    }
}

@Composable
fun SentenceUpdateButton(
    onSentencesUpdateButtonClicked: () -> Unit,
    modifier: Modifier
) {
    Button(
        modifier = modifier
            .padding(32.dp)
            .clip(CircleShape),
        onClick = onSentencesUpdateButtonClicked,
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
    ) {
        Text(
            text = "Update",
            fontSize = 32.sp
        )
    }
}