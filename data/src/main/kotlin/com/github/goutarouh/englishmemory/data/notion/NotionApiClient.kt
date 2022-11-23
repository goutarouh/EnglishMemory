package com.github.goutarouh.englishmemory.data.notion

interface NotionApiClient {
    suspend fun retrieveBlockChildren(): Int
}

class NotionApiClientImpl(
    notionApiService: NotionApiService
): NotionApiClient {
    override suspend fun retrieveBlockChildren(): Int {
        TODO()
    }

}