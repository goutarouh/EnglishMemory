package com.github.goutarouh.englishmemory.app

import android.content.Context
import androidx.room.Room
import com.github.goutarouh.englishmemory.data.notion.NotionApiClient
import com.github.goutarouh.englishmemory.data.notion.NotionApiClientImpl
import com.github.goutarouh.englishmemory.data.notion.NotionApiService
import com.github.goutarouh.englishmemory.data.room.AppDatabase
import com.github.goutarouh.englishmemory.data.room.PhraseDao
import com.github.goutarouh.englishmemory.data.room.SentenceDao
import com.github.goutarouh.englishmemory.data.sentence.PhraseRepository
import com.github.goutarouh.englishmemory.data.sentence.PhraseRepositoryImpl
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
    fun providePhraseRepository(
        notionApiClient: NotionApiClient,
        phraseDao: PhraseDao
    ): PhraseRepository {
        return PhraseRepositoryImpl(notionApiClient, phraseDao)
    }

    @Singleton
    @Provides
    fun provideSentenceRepository(
        notionApiClient: NotionApiClient,
        sentenceDao: SentenceDao
    ): SentenceRepository {
        return SentenceRepositoryImpl(notionApiClient, sentenceDao)
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

    @Singleton
    @Provides
    fun provideDb(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "EnglishMemoryDatabase"
        ).build()
    }

    @Singleton
    @Provides
    fun provideSentenceDao(
        db: AppDatabase
    ): SentenceDao {
        return db.sentenceDao()
    }

    @Singleton
    @Provides
    fun providePhraseDao(
        db: AppDatabase
    ): PhraseDao {
        return db.phraseDao()
    }

}
