package com.github.goutarouh.englishmemory.data.sentence

interface SentenceRepository {

    suspend fun getSentences(): List<Sentence>

}