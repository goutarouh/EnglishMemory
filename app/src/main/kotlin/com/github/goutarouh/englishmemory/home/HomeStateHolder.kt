package com.github.goutarouh.englishmemory.home

import android.content.Context
import android.util.Log
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
    private val isUpdateLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val homeState = combine(sentences, snackBarText, isUpdateLoading) { sentences, snackBarText, isUpdateLoading ->
        if (sentences == null) {
            return@combine HomeState.Loading
        }

        return@combine HomeState.Success(
            currentRegisteredSentencesNum = sentences.size,
            snackBarText = snackBarText,
            isUpdateLoading = isUpdateLoading
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
                isUpdateLoading.emit(true)
                val result = sentenceRepository.fetchSentences("6635eca0-ec99-45d9-8ffb-383632ae6370")
                sentences.emit(result)
                snackBarText.emit("${result.size}件のデータを更新しました。")
            } catch (e: Exception) {
                Log.e(TAG, "${e.message}", e)
                snackBarText.emit("更新に失敗しました。")
            } finally {
                isUpdateLoading.emit(false)
            }
        }
    }

    fun closeSnackBar() {
        coroutineScope.launch {
            snackBarText.emit(null)
        }
    }

    companion object {
        private val TAG = HomeStateHolder::class.java.name
    }

}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface HomeStateHolderEntryPoint {
    fun sentenceRepository(): SentenceRepository
}