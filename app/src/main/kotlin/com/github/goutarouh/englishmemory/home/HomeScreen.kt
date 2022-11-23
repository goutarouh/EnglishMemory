package com.github.goutarouh.englishmemory.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onSentencesUpdateButtonClicked: () -> Unit,
    onAutoPlayButtonClicked: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        HomeContent(
            onSentencesUpdateButtonClicked = onSentencesUpdateButtonClicked,
            onAutoPlayButtonClicked = onAutoPlayButtonClicked
        )
    }
}

@Composable
fun HomeContent(
    onSentencesUpdateButtonClicked: () -> Unit,
    onAutoPlayButtonClicked: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AutoPlayStartButton(
            onAutoPlayButtonClicked = onAutoPlayButtonClicked,
            modifier = Modifier.align(Alignment.Center)
        )
        CurrentSentencesStatus(
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
                text = "XXX",
                fontSize = 32.sp
            )
        }
        Row {
           Text(
               text = "最終更新日時:  ",
               fontSize = 32.sp
           )
           Text(
               text = "MM:DD",
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
            .background(color = MaterialTheme.colors.secondary)
            .padding(32.dp),
        onClick = onSentencesUpdateButtonClicked
    ) {
        Text(
            text = "Update",
            fontSize = 32.sp
        )
    }
}