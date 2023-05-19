package com.example.translatorkmmed.translate.presentation

import com.example.translatorkmmed.core.presentation.UiLanguage
import com.example.translatorkmmed.translate.domain.translate.TranslateErrorEnum

data class TranslateState(
    val fromText: String = "",
    val toText: String? = null,
    val isTranslating: Boolean = false,
    val fromLanguage: UiLanguage = UiLanguage.byCode("en"),
    val toLanguage: UiLanguage = UiLanguage.byCode("de"),
    val isChoosingFromLanguage: Boolean = false,
    val isChoosingToLanguage: Boolean = false,
    val error: TranslateErrorEnum? = null,
)
