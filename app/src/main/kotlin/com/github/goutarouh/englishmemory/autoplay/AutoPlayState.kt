package com.github.goutarouh.englishmemory.autoplay

import com.github.goutarouh.englishmemory.data.sentence.Sentence

sealed class AutoPlayState {

    object Loading: AutoPlayState()

    data class Success(
        val sentence: Sentence
    ): AutoPlayState()

}