package com.ps.androidgpt.di

import com.ps.androidgpt.data.remote.ChatApi
import com.ps.androidgpt.data.repository.ChatRepositoryImpl
import com.ps.androidgpt.domain.repository.ChatRepository
import com.ps.androidgpt.domain.use_case.get_response.GetResponseUseCase
import com.ps.androidgpt.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideChatGptApi() : ChatApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGptRepository(chatApi: ChatApi) : ChatRepository {
        return ChatRepositoryImpl(chatApi = chatApi)
    }

    @Provides
    @Singleton
    fun provideGetResponseUseCase(chatRepository: ChatRepository) : GetResponseUseCase {
        return GetResponseUseCase(chatRepository = chatRepository)
    }

}