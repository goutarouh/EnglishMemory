package com.github.goutarouh.englishmemory.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhraseDao {

    @Query("SELECT * FROM PhraseForRoom")
    fun getAll(): List<PhraseForRoom>

    @Insert
    fun insertAll(vararg phraseForRoom: PhraseForRoom)

    @Query("DELETE FROM PhraseForRoom")
    fun deleteAll()

}