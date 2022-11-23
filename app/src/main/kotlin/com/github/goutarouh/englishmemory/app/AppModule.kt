package com.github.goutarouh.englishmemory.app

import android.content.Context
import com.github.goutarouh.englishmemory.data.csv.AssetsCsvReader
import com.github.goutarouh.englishmemory.data.csv.CsvReader
import com.github.goutarouh.englishmemory.data.notion.NotionApiClient
import com.github.goutarouh.englishmemory.data.notion.NotionApiClientImpl
import com.github.goutarouh.englishmemory.data.notion.NotionApiService
import com.github.goutarouh.englishmemory.data.sentence.SentenceRepository
import com.github.goutarouh.englishmemory.data.sentence.SentenceRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
    fun provideSentenceRepository(csvReader: CsvReader, notionApiClient: NotionApiClient): SentenceRepository {
        return SentenceRepositoryImpl(csvReader, notionApiClient)
    }

    @Singleton
    @Provides
    fun provideNotionApiRetrofitService(): NotionApiService {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

       val retrofit = Retrofit.Builder()
           .baseUrl("https://api.notion.com/")
           .addConverterFactory(MoshiConverterFactory.create(moshi))
           .build()
        return retrofit.create(NotionApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideNotionApiClient(
        notionApiService: NotionApiService
    ): NotionApiClient {
        return NotionApiClientImpl(notionApiService)
    }

}
