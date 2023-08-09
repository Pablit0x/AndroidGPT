package com.ps.androidgpt.di

import com.ps.androidgpt.data.ChatGPTService
import com.ps.androidgpt.data.repository.GptRepositoryImpl
import com.ps.androidgpt.domain.repository.GptRepository
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
    fun provideChatGptApi() : ChatGPTService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatGPTService::class.java)
    }

    @Provides
    @Singleton
    fun provideGptRepository(chatGptApi: ChatGPTService) : GptRepository {
        return GptRepositoryImpl(chatGptApi)
    }

}