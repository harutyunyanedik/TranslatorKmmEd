package com.example.translatorkmmed.translate.domain.translate

import com.example.translatorkmmed.core.domain.launguage.Language

interface TranslateClient {
    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String
}