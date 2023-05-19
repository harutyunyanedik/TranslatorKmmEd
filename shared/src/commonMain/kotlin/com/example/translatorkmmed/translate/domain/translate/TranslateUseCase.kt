package com.example.translatorkmmed.translate.domain.translate

import com.example.translatorkmmed.core.domain.launguage.Language
import com.example.translatorkmmed.core.domain.util.Resource

class TranslateUseCase(private val client: TranslateClient) {

    suspend fun execute(fromLanguage: Language, fromText: String, toLanguage: Language): Resource<String> {
        return try {
            val translatedText = client.translate(fromLanguage, fromText, toLanguage)
            Resource.Success(translatedText)
        } catch(e: TranslateException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}