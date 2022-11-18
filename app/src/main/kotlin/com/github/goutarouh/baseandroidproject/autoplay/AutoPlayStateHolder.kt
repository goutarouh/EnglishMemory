package com.github.goutarouh.baseandroidproject.autoplay

import com.github.goutarouh.baseandroidproject.data.sentence.Sentence
import com.github.goutarouh.baseandroidproject.data.sentence.SentenceRepository
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
    private val ticker: MutableSharedFlow<Int> = MutableSharedFlow()

    val sentence = combine(sentences, ticker) { sentences, index ->
        if (sentences == null) {
            return@combine AutoPlayState.Loading
        }
        if (sentences.isEmpty()) {
            return@combine AutoPlayState.Loading
        }

        return@combine AutoPlayState.Success(sentences[index % sentences.size])
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), AutoPlayState.Loading)

    fun setUp() {
        coroutineScope.launch {
            sentences.emit(sentenceRepository.getSentences())
            var i = 0
            while (true) {
                ticker.emit(i)
                i++
                delay(3000)
            }
        }
    }

    fun dispose() {
        coroutineScope.cancel()
    }
}