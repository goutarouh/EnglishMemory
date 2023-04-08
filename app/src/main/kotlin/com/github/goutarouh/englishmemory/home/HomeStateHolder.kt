package com.github.goutarouh.englishmemory.home

import android.content.Context
import android.util.Log
import com.github.goutarouh.englishmemory.data.sentence.Phrase
import com.github.goutarouh.englishmemory.data.sentence.PhraseRepository
import com.github.goutarouh.englishmemory.data.sentence.Sentence
import com.github.goutarouh.englishmemory.data.sentence.SentenceRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.Random

class HomeStateHolder(
    context: Context,
    private val coroutineScope: CoroutineScope
) {

    private val hiltEntryPoint = EntryPointAccessors.fromApplication<HomeStateHolderEntryPoint>(context)
    private val sentenceRepository: SentenceRepository = hiltEntryPoint.sentenceRepository()
    private val phraseRepository: PhraseRepository = hiltEntryPoint.phraseRepository()

    private val phrases: MutableStateFlow<List<Phrase>> = MutableStateFlow(listOf())
    private val sentences: MutableStateFlow<List<Sentence>> = MutableStateFlow(listOf())
    private val ticker: MutableStateFlow<Int> = MutableStateFlow(1)

    val state = combine(sentences, phrases, ticker) { sentences, phrase, ticker ->
        if (ticker > 0) {
            return@combine HomeState(sentences, listOf())
        } else {
            return@combine HomeState(listOf(), phrase)
        }
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), HomeState())

    fun setUp() {
        coroutineScope.launch {
            phrases.emit(phraseRepository.getPhrases())
            sentences.emit(sentenceRepository.getSentences())

            while (true) {
                ticker.update { it * -1 }
                delay(1 * 1000) // 30[s]
                if (!isActive) {
                    return@launch
                }
            }
        }
    }

    fun dispose() {
        coroutineScope.cancel()
    }

    companion object {
        private val TAG = HomeStateHolder::class.java.name
    }

}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface HomeStateHolderEntryPoint {
    fun sentenceRepository(): SentenceRepository
    fun phraseRepository(): PhraseRepository
}