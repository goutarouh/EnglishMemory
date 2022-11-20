package com.github.goutarouh.englishmemory.app

import android.content.Context
import com.github.goutarouh.englishmemory.data.csv.AssetsCsvReader
import com.github.goutarouh.englishmemory.data.csv.CsvReader
import com.github.goutarouh.englishmemory.data.sentence.SentenceRepository
import com.github.goutarouh.englishmemory.data.sentence.SentenceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCsvReader(@ApplicationContext context: Context): CsvReader {
        return AssetsCsvReader(context)
    }

    @Singleton
    @Provides
    fun provideSentenceRepository(csvReader: CsvReader): SentenceRepository {
        return SentenceRepositoryImpl(csvReader)
    }

}
