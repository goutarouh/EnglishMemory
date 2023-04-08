package com.github.goutarouh.englishmemory.data.sentence

import com.github.goutarouh.englishmemory.data.notion.NotionApiClient
import com.github.goutarouh.englishmemory.data.room.PhraseDao
import com.github.goutarouh.englishmemory.data.room.PhraseForRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class Phrase(
    val en: String,
    val ja: String
)

interface PhraseRepository {
    suspend fun getPhrases(): List<Phrase>
    suspend fun fetchPhrases(blockId: String): List<Phrase>
}

class PhraseRepositoryImpl @Inject constructor(
    val notionApiClient: NotionApiClient,
    val phraseDao: PhraseDao,
): PhraseRepository {
    override suspend fun getPhrases(): List<Phrase> {
        val cacheResult = withContext(Dispatchers.IO) {
            phraseDao.getAll()
        }
        val phrases = cacheResult.map {
            Phrase(
                en = it.en,
                ja = it.ja
            )
        }
        return phrases
    }

    override suspend fun fetchPhrases(blockId: String): List<Phrase> {
        val apiResult = notionApiClient.retrieveBlockChildren(blockId)

        withContext(Dispatchers.IO) {
            phraseDao.deleteAll()
        }

        val phraseForRoom = apiResult.map {
            PhraseForRoom(
                en = it.en,
                ja = it.ja
            )
        }

        withContext(Dispatchers.IO) {
            phraseDao.insertAll(*phraseForRoom.toTypedArray())
        }

        val cacheResult = withContext(Dispatchers.IO) {
            phraseDao.getAll()
        }
        val phrases = cacheResult.map {
            Phrase(
                en = it.en,
                ja = it.ja
            )
        }
        return phrases
    }

}