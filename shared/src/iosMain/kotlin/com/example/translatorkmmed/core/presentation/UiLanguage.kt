package com.example.translatorkmmed.core.presentation

import com.example.translatorkmmed.core.domain.launguage.Language

actual class UiLanguage(actual val language: Language, val imageName: String) {

    actual companion object {

        actual fun byCode(langCode: String): UiLanguage {
            return allLanguages.find { it.language.langCode == langCode }
                ?: throw IllegalArgumentException("Invalid or unsupported language code")
        }

        actual val allLanguages: List<UiLanguage>
            get() = Language.values().map {
                UiLanguage(language = it, imageName = it.langName.lowercase())
            }
    }
}