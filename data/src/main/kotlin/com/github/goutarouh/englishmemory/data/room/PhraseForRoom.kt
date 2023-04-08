package com.github.goutarouh.englishmemory.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class PhraseForRoom(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val ja: String,
    val en: String
)
