package com.github.goutarouh.englishmemory.autoplay

import com.github.goutarouh.englishmemory.data.sentence.Sentence

sealed class AutoPlayState {

    object Loading: AutoPlayState()

    object End: AutoPlayState()

    data class Success(
        val sentence: Sentence,
        val ticker: Int
    ): AutoPlayState()

}