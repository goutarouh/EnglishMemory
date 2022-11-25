package com.github.goutarouh.englishmemory.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

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
                    onAutoPlayButtonClicked = onAutoPlayButtonClicked,
                    onSnackBarEnd = {
                        stateHolder.closeSnackBar()
                    }
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
    onAutoPlayButtonClicked: () -> Unit,
    onSnackBarEnd: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AutoPlayStartButton(
            onAutoPlayButtonClicked = onAutoPlayButtonClicked,
            modifier = Modifier.align(Alignment.Center)
        )
        CurrentSentencesStatus(
            currentRegisteredSentencesNum = state.currentRegisteredSentencesNum,
            modifier = Modifier.align(Alignment.TopEnd)
        )
        SentenceUpdateButton(
            onSentencesUpdateButtonClicked = onSentencesUpdateButtonClicked,
            modifier = Modifier.align(Alignment.BottomEnd)
        )

        if (state.snackBarText != null) {
            SnackBar(
                text = state.snackBarText,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                onSnackBarEnd()
            }
        }

        if (state.isUpdateLoading) {
            UpdateLoadingIndicator()
        }
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
                text = "$currentRegisteredSentencesNum",
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
    Card(
        shape = RoundedCornerShape(30.dp),
        modifier = modifier
            .padding(32.dp)
            .clip(RoundedCornerShape(30.dp))
            .clickable {
                onSentencesUpdateButtonClicked()
            }
    ) {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colors.secondary)
                .padding(12.dp)
        ) {
            Text(
                text = "Update",
                fontSize = 32.sp
            )
        }
    }
}

@Composable
fun UpdateLoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize().background(color = Color(0xDDDDDDDD))
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp)
        )
    }
}

@Composable
fun SnackBar(
    text: String,
    modifier: Modifier,
    onSnackBarEnd: () -> Unit
) {

    LaunchedEffect(text) {
        delay(8000)
        onSnackBarEnd()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .widthIn(max = 300.dp)
            .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
            .background(color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        Text(
            text = "閉じる",
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 32.dp)
                .clickable {
                    onSnackBarEnd()
                }
        )
    }
}