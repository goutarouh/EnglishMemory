package com.github.goutarouh.englishmemory.data.sentence

import com.github.goutarouh.englishmemory.data.csv.CsvReader
import com.github.goutarouh.englishmemory.data.notion.NotionApiClient
import javax.inject.Inject

class SentenceRepositoryImpl @Inject constructor(
    val csvReader: CsvReader,
    val notionApiClient: NotionApiClient
): SentenceRepository {

    // TODO DBに保存する
    var sentences: List<Sentence> = listOf()

    override suspend fun getSentences(): List<Sentence> {
        return sentences
    }

    override suspend fun fetchSentences(blockId: String) {
        sentences = notionApiClient.retrieveBlockChildren(blockId)
    }


}