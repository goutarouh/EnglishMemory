package com.github.goutarouh.englishmemory.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SentenceDao {

    @Query("SELECT * FROM SentenceForRoom")
    fun getAll(): List<SentenceForRoom>

    @Insert
    fun insertAll(vararg sentenceForRoom: SentenceForRoom)

    @Query("DELETE FROM SentenceForRoom")
    fun deleteAll()

}