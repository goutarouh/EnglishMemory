package com.github.goutarouh.englishmemory.data.sentence

class SentenceRepositoryImpl: SentenceRepository {
    override suspend fun getSentences(): List<Sentence> {
        return sentences
    }
}