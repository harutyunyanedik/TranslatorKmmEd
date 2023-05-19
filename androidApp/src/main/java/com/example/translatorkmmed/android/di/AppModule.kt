package com.example.translatorkmmed.android.di

import com.example.translatorkmmed.translate.data.remote.HttpClientFactory
import com.example.translatorkmmed.translate.data.translate.KtorTranslateClient
import com.example.translatorkmmed.translate.domain.translate.TranslateClient
import com.example.translatorkmmed.translate.domain.translate.TranslateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClientFactory().create()

    @Provides
    @Singleton
    fun provideTranslateClient(httpClient: HttpClient): TranslateClient = KtorTranslateClient(httpClient)


    @Provides
    @Singleton
    fun provideTranslateUseCase(client: TranslateClient): TranslateUseCase = TranslateUseCase(client)

}