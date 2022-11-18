package com.github.goutarouh.baseandroidproject.autoplay

import com.github.goutarouh.baseandroidproject.data.sentence.Sentence

sealed class AutoPlayState {

    object Loading: AutoPlayState()

    data class Success(
        val sentence: Sentence
    ): AutoPlayState()

}