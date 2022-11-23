package com.github.goutarouh.englishmemory.app

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
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSentenceRepository(notionApiClient: NotionApiClient): SentenceRepository {
        return SentenceRepositoryImpl(notionApiClient)
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
