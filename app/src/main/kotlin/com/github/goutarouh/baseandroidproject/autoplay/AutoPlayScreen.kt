package com.github.goutarouh.baseandroidproject.autoplay

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.github.goutarouh.baseandroidproject.data.sentence.Sentence
import com.github.goutarouh.baseandroidproject.data.sentence.SentenceRepositoryImpl

@Composable
fun AutoPlayScreen() {

    val coroutineScope = rememberCoroutineScope()
    val stateHolder by remember { mutableStateOf(AutoPlayStateHolder(coroutineScope, SentenceRepositoryImpl())) }
    DisposableEffect(stateHolder) {
        stateHolder.setUp()
        onDispose { stateHolder.dispose() }
    }

    val state by stateHolder.sentence.collectAsState()
    AutoPlayContent(state = state)
}
@Composable
fun AutoPlayContent(
    state: AutoPlayState,
    modifier: Modifier = Modifier
) {
    when (state) {
        is AutoPlayState.Loading -> Loading(modifier)
        is AutoPlayState.Success -> Success(state.sentence, modifier)
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun Success(
    sentence: Sentence,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = sentence.en, fontSize = 32.sp, lineHeight = 24.sp)
        Text(text = sentence.ja, fontSize = 32.sp, lineHeight = 24.sp)
    }
}
