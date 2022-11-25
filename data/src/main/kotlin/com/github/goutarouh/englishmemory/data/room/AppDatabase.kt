package com.github.goutarouh.englishmemory.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SentenceForRoom::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun sentenceDao(): SentenceDao
}