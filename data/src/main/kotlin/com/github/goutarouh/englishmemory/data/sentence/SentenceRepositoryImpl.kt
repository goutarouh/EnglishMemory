package com.github.goutarouh.englishmemory.data.sentence

import com.github.goutarouh.englishmemory.data.notion.NotionApiClient
import javax.inject.Inject

class SentenceRepositoryImpl @Inject constructor(
    val notionApiClient: NotionApiClient
): SentenceRepository {

    // TODO DBに保存する
    var sentences: List<Sentence> = listOf()

    override suspend fun getSentences(): List<Sentence> {
        return sentences
    }

    override suspend fun fetchSentences(blockId: String): List<Sentence> {
        val result = notionApiClient.retrieveBlockChildren(blockId)
        sentences = result
        return result
    }


}