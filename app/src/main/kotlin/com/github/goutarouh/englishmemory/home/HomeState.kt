package com.github.goutarouh.englishmemory.home

import com.github.goutarouh.englishmemory.data.sentence.Phrase
import com.github.goutarouh.englishmemory.data.sentence.Sentence


data class HomeState(
    val sentences: List<Sentence> = listOf(),
    val phrases: List<Phrase> = listOf()
)