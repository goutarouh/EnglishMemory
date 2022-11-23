package com.github.goutarouh.englishmemory.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SentenceForRoom(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val ja: String,

    val en: String
)