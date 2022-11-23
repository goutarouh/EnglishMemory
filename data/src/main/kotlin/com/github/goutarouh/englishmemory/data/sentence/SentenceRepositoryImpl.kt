package com.github.goutarouh.englishmemory.data.sentence

import com.github.goutarouh.englishmemory.data.csv.CsvReader
import com.github.goutarouh.englishmemory.data.notion.NotionApiClient
import javax.inject.Inject

class SentenceRepositoryImpl @Inject constructor(
    val csvReader: CsvReader,
    val notionApiClient: NotionApiClient
): SentenceRepository {
    override suspend fun getSentences(): List<Sentence> {
        return csvReader.readSentences()
    }
}