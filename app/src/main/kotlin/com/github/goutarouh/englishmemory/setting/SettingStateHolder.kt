package com.github.goutarouh.englishmemory.setting

import android.content.Context
import com.github.goutarouh.englishmemory.data.sentence.PhraseRepository
import com.github.goutarouh.englishmemory.data.sentence.SentenceRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingStateHolder(
    context: Context,
    private val coroutineScope: CoroutineScope,
) {

    private val hiltEntryPoint = EntryPointAccessors.fromApplication<SettingStateHolderEntryPoint>(context)
    private val sentenceRepository: SentenceRepository = hiltEntryPoint.sentenceRepository()
    private val phraseRepository: PhraseRepository = hiltEntryPoint.phraseRepository()

    private val _state = MutableStateFlow(false)
    val state = _state.asStateFlow()

    fun fetchAllInformation() {
        coroutineScope.launch {
            _state.emit(true)
            try {
                sentenceRepository.fetchSentences("6635eca0-ec99-45d9-8ffb-383632ae6370")
                phraseRepository.fetchPhrases("34ae4d82-f35e-49ec-b432-edae9fbc9fb8")
            } catch (e: Exception) {
                // no-op
            } finally {
                _state.emit(false)
            }
        }
    }

    fun dispose() {
        coroutineScope.cancel()
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SettingStateHolderEntryPoint {
    fun sentenceRepository(): SentenceRepository
    fun phraseRepository(): PhraseRepository
}