package com.github.goutarouh.englishmemory.autoplay

import com.github.goutarouh.englishmemory.data.sentence.Sentence
import com.github.goutarouh.englishmemory.data.sentence.SentenceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AutoPlayStateHolder(
    private val coroutineScope: CoroutineScope,
    private val sentenceRepository: SentenceRepository
) {
    private val sentences: MutableStateFlow<List<Sentence>?> = MutableStateFlow(null)
    private val ticker: MutableStateFlow<Int> = MutableStateFlow(0)

    val sentence = combine(sentences, ticker) { sentences, ticker ->
        if (sentences == null) {
            return@combine AutoPlayState.Loading
        }
        if (sentences.isEmpty()) {
            return@combine AutoPlayState.Loading
        }

        return@combine AutoPlayState.Success(sentences[ticker % sentences.size])
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), AutoPlayState.Loading)

    fun setUp() {
        coroutineScope.launch {
            sentences.emit(sentenceRepository.getSentences())
        }
    }

    fun dispose() {
        coroutineScope.cancel()
    }


    fun requestNextSentence() {
        ticker.update { it + 1 }
    }
}