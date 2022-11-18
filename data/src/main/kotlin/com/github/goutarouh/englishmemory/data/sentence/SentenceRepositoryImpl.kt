package com.github.goutarouh.englishmemory.data.sentence

class SentenceRepositoryImpl: SentenceRepository {
    override fun getSentences(): List<Sentence> {
        return sentences
    }
}