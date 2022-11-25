package com.github.goutarouh.englishmemory.data.notion

import com.github.goutarouh.englishmemory.data.sentence.Sentence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface NotionApiClient {
    suspend fun retrieveBlockChildren(blockId: String): List<Sentence>
}

class NotionApiClientImpl(
    private val notionApiService: NotionApiService
): NotionApiClient {
    override suspend fun retrieveBlockChildren(blockId: String): List<Sentence> {
        val response = withContext(Dispatchers.IO) {
            notionApiService.retrieveBlockChildren(blockId).execute()
        }
        if (response.isSuccessful) {
            return response.body()?.results?.mapIndexedNotNull { index, item ->

                if (index == 0) return@mapIndexedNotNull null

                val en = item.tableRow.cells.getOrNull(0)?.getOrNull(0)?.plainText
                val ja = item.tableRow.cells.getOrNull(1)?.getOrNull(0)?.plainText

                if (en == null || ja == null) {
                    return@mapIndexedNotNull null
                }

                Sentence(
                    en = en,
                    ja = ja
                )
            } ?: throw IllegalStateException()
        } else {
            throw IllegalStateException()
        }
    }

}