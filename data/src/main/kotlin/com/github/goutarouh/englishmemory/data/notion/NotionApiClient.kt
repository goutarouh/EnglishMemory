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
            return response.body()?.results?.mapNotNull {
                val text = it.paragraph.richText.getOrNull(0)?.plainText ?: return@mapNotNull null
                if (text.isEmpty()) return@mapNotNull null
                val enAndJa = text.split("\n")
                Sentence(
                    en = enAndJa.getOrNull(0) ?: "",
                    ja = enAndJa.getOrNull(1) ?: ""
                )
            } ?: throw IllegalStateException()
        } else {
            throw IllegalStateException()
        }
    }

}