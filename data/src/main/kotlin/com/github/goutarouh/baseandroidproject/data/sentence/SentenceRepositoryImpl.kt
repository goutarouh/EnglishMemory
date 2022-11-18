package com.github.goutarouh.baseandroidproject.data.sentence

class SentenceRepositoryImpl: SentenceRepository {
    override fun getSentences(): List<Sentence> {
        return sentences
    }
}