package com.example.translatorkmmed.core.presentation

import com.example.translatorkmmed.core.domain.launguage.Language

expect class UiLanguage {

    val language: Language

    companion object {
        fun byCode(langCode: String): UiLanguage
        val allLanguages: List<UiLanguage>
    }
}