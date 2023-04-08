package com.github.goutarouh.englishmemory.autoplay

import android.content.Context
import com.github.goutarouh.englishmemory.data.sentence.PhraseRepository
import com.github.goutarouh.englishmemory.data.sentence.Sentence
import com.github.goutarouh.englishmemory.data.sentence.SentenceRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AutoPlayStateHolder(
    context: Context,
    private val coroutineScope: CoroutineScope,
) {

    private val hiltEntryPoint = EntryPointAccessors.fromApplication<AutoPlayStateHolderEntryPoint>(context)
    private val sentenceRepository: SentenceRepository = hiltEntryPoint.sentenceRepository()
    private val phraseRepository: PhraseRepository = hiltEntryPoint.phraseRepository()

    private val sentences: MutableStateFlow<List<Sentence>?> = MutableStateFlow(null)
    private val ticker: MutableStateFlow<Int> = MutableStateFlow(0)

    val sentence = combine(sentences, ticker) { sentences, ticker ->

        if (ticker == 9) {
            return@combine AutoPlayState.End
        }

        if (sentences == null) {
            return@combine AutoPlayState.Loading
        }
        if (sentences.isEmpty()) {
            return@combine AutoPlayState.Loading
        }

        val index = sentences.indices.random()
        return@combine AutoPlayState.Success(sentences[index], ticker)
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


@EntryPoint
@InstallIn(SingletonComponent::class)
interface AutoPlayStateHolderEntryPoint {
    fun sentenceRepository(): SentenceRepository
    fun phraseRepository(): PhraseRepository
}