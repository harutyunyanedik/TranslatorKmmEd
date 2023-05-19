package com.example.translatorkmmed.translate.presentation

import com.example.translatorkmmed.core.presentation.UiLanguage

sealed class TranslateEvent {
    data class ChooseFromLanguage(val language: UiLanguage): TranslateEvent()

    data class ChooseToLanguage(val language: UiLanguage): TranslateEvent()

    object StopChoosingLanguage: TranslateEvent()

    object SwapLanguages: TranslateEvent()

    data class ChangeTranslationText(val text: String): TranslateEvent()

    object Translate: TranslateEvent()

    object OpenFromLanguageDropDown: TranslateEvent()

    object OpenToLanguageDropDown: TranslateEvent()

    object CloseTranslation: TranslateEvent()

    object EditTranslation: TranslateEvent()

    object OnErrorSeen: TranslateEvent()
}