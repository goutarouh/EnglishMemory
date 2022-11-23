package com.github.goutarouh.englishmemory.home


sealed class HomeState {
    object Loading: HomeState()

    data class Success(
        val currentRegisteredSentencesNum: Int,
        val snackBarText: String?,
        val isUpdateLoading: Boolean
    ): HomeState()
}
