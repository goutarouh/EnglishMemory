package com.github.goutarouh.englishmemory.home

import android.content.Context
import com.github.goutarouh.englishmemory.data.sentence.Sentence
import com.github.goutarouh.englishmemory.data.sentence.SentenceRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeStateHolder(
    context: Context,
    private val coroutineScope: CoroutineScope
) {

    private val hiltEntryPoint = EntryPointAccessors.fromApplication<HomeStateHolderEntryPoint>(context)
    private val sentenceRepository = hiltEntryPoint.sentenceRepository()

    private val sentences: MutableStateFlow<List<Sentence>?> = MutableStateFlow(null)

    val homeState = combine(sentences) { sentences ->
        if (sentences == null) {
            return@combine HomeState.Loading
        }

        return@combine HomeState.Success(
            currentRegisteredSentencesNum = sentences.size,
            lastUpdated = "11:00"
        )
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), HomeState.Loading)

    fun setUp() {
        coroutineScope.launch {
            sentences.emit(sentenceRepository.getSentences())
        }
    }

    fun dispose() {
        coroutineScope.cancel()
    }

}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface HomeStateHolderEntryPoint {
    fun sentenceRepository(): SentenceRepository
}