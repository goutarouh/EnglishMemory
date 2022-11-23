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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeStateHolder(
    context: Context,
    private val coroutineScope: CoroutineScope
) {

    private val hiltEntryPoint = EntryPointAccessors.fromApplication<HomeStateHolderEntryPoint>(context)
    private val sentenceRepository: SentenceRepository = hiltEntryPoint.sentenceRepository()

    private val sentences: MutableStateFlow<List<Sentence>?> = MutableStateFlow(null)
    private val snackBarText: MutableStateFlow<String?> = MutableStateFlow(null)

    val homeState = combine(sentences, snackBarText) { sentences, snackBarText ->
        if (sentences == null) {
            return@combine HomeState.Loading
        }

        return@combine HomeState.Success(
            currentRegisteredSentencesNum = sentences.size,
            lastUpdated = "16:00",
            snackBarText = snackBarText
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

    fun updateSentences() {
        coroutineScope.launch {
            try {
                val result = sentenceRepository.fetchSentences("29698e1418684ed1b5bc044eacc77006")
                sentences.emit(result)
                snackBarText.emit("${result.size}件のデータを更新しました。")
            } catch (e: Exception) {
                snackBarText.emit("更新に失敗しました。")
            }
        }
    }

    fun closeSnackBar() {
        coroutineScope.launch {
            snackBarText.emit(null)
        }
    }

}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface HomeStateHolderEntryPoint {
    fun sentenceRepository(): SentenceRepository
}