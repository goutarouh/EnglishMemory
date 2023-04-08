package com.github.goutarouh.englishmemory.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SentenceForRoom::class, PhraseForRoom::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun sentenceDao(): SentenceDao

    abstract fun phraseDao(): PhraseDao
}