package com.ps.androidgpt.di

import com.ps.androidgpt.data.local.entity.ChatEntryEntity
import com.ps.androidgpt.data.remote.ChatApi
import com.ps.androidgpt.data.repository.ChatRepositoryImpl
import com.ps.androidgpt.domain.repository.ChatRepository
import com.ps.androidgpt.domain.use_case.delete_entry.DeleteEntryUseCase
import com.ps.androidgpt.domain.use_case.get_response.GetResponseUseCase
import com.ps.androidgpt.domain.use_case.get_saved_entries.GetSavedEntriesUseCase
import com.ps.androidgpt.domain.use_case.save_entry.InsertChatEntryUseCase
import com.ps.androidgpt.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRealm(): Realm {
        val realmConfig = RealmConfiguration.Builder(
            schema = setOf(ChatEntryEntity::class)
        ).compactOnLaunch().build()
        return Realm.open(configuration = realmConfig)
    }

    @Provides
    @Singleton
    fun provideChatGptApi(): ChatApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(ChatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGptRepository(chatApi: ChatApi, realm: Realm): ChatRepository {
        return ChatRepositoryImpl(chatApi = chatApi, realm = realm)
    }

    @Provides
    @Singleton
    fun provideGetResponseUseCase(chatRepository: ChatRepository): GetResponseUseCase {
        return GetResponseUseCase(chatRepository = chatRepository)
    }

    @Provides
    @Singleton
    fun provideInsertChatEntryUseCase(chatRepository: ChatRepository): InsertChatEntryUseCase {
        return InsertChatEntryUseCase(chatRepository = chatRepository)
    }

    @Provides
    @Singleton
    fun provideGetSavedEntriesUseCase(chatRepository: ChatRepository): GetSavedEntriesUseCase {
        return GetSavedEntriesUseCase(chatRepository = chatRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteEntryUseCase(chatRepository: ChatRepository): DeleteEntryUseCase {
        return DeleteEntryUseCase(chatRepository = chatRepository)
    }


}