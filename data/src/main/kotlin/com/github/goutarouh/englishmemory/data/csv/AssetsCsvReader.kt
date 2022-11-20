package com.github.goutarouh.englishmemory.data.csv

import android.content.Context
import com.github.goutarouh.englishmemory.data.sentence.Sentence
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

interface CsvReader {
    suspend fun readSentences(): List<Sentence>
}

class AssetsCsvReader(
    @ApplicationContext private val context: Context
): CsvReader {

    companion object {
        private const val FILE_NAME = "my_english_sentences.csv"
    }

    private val assetManager = context.resources.assets

    override suspend fun readSentences(): List<Sentence> {
        return try {
            val sentences = mutableListOf<Sentence>()
            val file = assetManager.open(FILE_NAME)
            val fileReader = BufferedReader(InputStreamReader(file))
            fileReader.forEachLine {
                val items = it.split(",")
                val sentence = Sentence(en = items[0], ja = items[1])
                sentences.add(sentence)
            }
            sentences
        } catch (e: IOException) {
            listOf()
        }
    }
}