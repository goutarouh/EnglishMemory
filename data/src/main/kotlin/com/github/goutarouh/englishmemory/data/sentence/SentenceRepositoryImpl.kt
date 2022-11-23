package com.github.goutarouh.englishmemory.data.sentence

import com.github.goutarouh.englishmemory.data.notion.NotionApiClient
import com.github.goutarouh.englishmemory.data.room.SentenceDao
import com.github.goutarouh.englishmemory.data.room.SentenceForRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SentenceRepositoryImpl @Inject constructor(
    val notionApiClient: NotionApiClient,
    val sentenceDao: SentenceDao,
): SentenceRepository {

    override suspend fun getSentences(): List<Sentence> {
        val cacheResult = withContext(Dispatchers.IO) {
            sentenceDao.getAll()
        }
        val sentences = cacheResult.map {
            Sentence(
                en = it.en,
                ja = it.ja
            )
        }
        return sentences
    }

    override suspend fun fetchSentences(blockId: String): List<Sentence> {
        val apiResult = notionApiClient.retrieveBlockChildren(blockId)

        withContext(Dispatchers.IO) {
            sentenceDao.deleteAll()
        }

        val sentenceForRoom = apiResult.map {
            SentenceForRoom(
                en = it.en,
                ja = it.ja
            )
        }

        withContext(Dispatchers.IO) {
            sentenceDao.insertAll(*sentenceForRoom.toTypedArray())
        }

        val cacheResult = withContext(Dispatchers.IO) {
            sentenceDao.getAll()
        }
        val sentences = cacheResult.map {
            Sentence(
                en = it.en,
                ja = it.ja
            )
        }
        return sentences
    }


}