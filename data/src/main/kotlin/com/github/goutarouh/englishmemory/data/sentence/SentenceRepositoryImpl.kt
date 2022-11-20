package com.github.goutarouh.englishmemory.data.sentence

import com.github.goutarouh.englishmemory.data.csv.CsvReader
import javax.inject.Inject

class SentenceRepositoryImpl @Inject constructor(
    val csvReader: CsvReader
): SentenceRepository {
    override suspend fun getSentences(): List<Sentence> {
        return csvReader.readSentences()
    }
}