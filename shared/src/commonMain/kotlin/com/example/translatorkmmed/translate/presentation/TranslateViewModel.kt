package com.example.translatorkmmed.translate.presentation

import com.example.translatorkmmed.core.domain.util.Resource
import com.example.translatorkmmed.core.domain.util.toCommonStateFlow
import com.example.translatorkmmed.translate.domain.translate.TranslateException
import com.example.translatorkmmed.translate.domain.translate.TranslateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TranslateViewModel(
    private val translateUseCase: TranslateUseCase,
    private val coroutineScope: CoroutineScope?
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(TranslateState())
    val state = _state.asStateFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TranslateState()).toCommonStateFlow()

    private var translateJob: Job? = null

    fun onEvent(event: TranslateEvent) {
        when (event) {
            is TranslateEvent.ChangeTranslationText -> {
                _state.update {
                    it.copy(fromText = event.text)
                }
            }

            is TranslateEvent.ChooseFromLanguage -> {
                _state.update {
                    it.copy(isChoosingFromLanguage = false, fromLanguage = event.language)
                }
            }

            is TranslateEvent.ChooseToLanguage -> {
                _state.update {
                    it.copy(isChoosingToLanguage = false, toLanguage = event.language)
                }
            }

            TranslateEvent.CloseTranslation -> {
                _state.update {
                    it.copy(isTranslating = false, fromText = "", toText = null)
                }
            }

            TranslateEvent.EditTranslation -> {
                if (_state.value.toText != null) {
                    _state.update {
                        it.copy(toText = null, isTranslating = false)
                    }
                }
            }

            TranslateEvent.OnErrorSeen -> {
                _state.update { it.copy(error = null) }
            }

            TranslateEvent.OpenFromLanguageDropDown -> {
                _state.update { it.copy(isChoosingFromLanguage = true) }
            }

            TranslateEvent.OpenToLanguageDropDown -> {
                _state.update { it.copy(isChoosingToLanguage = true) }
            }

            TranslateEvent.StopChoosingLanguage -> {
                _state.update {
                    it.copy(
                        isChoosingFromLanguage = false,
                        isChoosingToLanguage = false
                    )
                }
            }

            TranslateEvent.SwapLanguages -> {
                _state.update {
                    it.copy(
                        fromLanguage = it.toLanguage,
                        toLanguage = it.fromLanguage,
                        fromText = it.toText ?: "",
                        toText = if (it.toText != null) it.fromText else null
                    )
                }
            }

            TranslateEvent.Translate -> {
                translate(state.value)
            }
            else -> Unit
        }
    }

    private fun translate(state: TranslateState) {
        if (state.isTranslating || state.fromText.isBlank()) {
            return
        }

        translateJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    isTranslating = true
                )
            }

            val result = translateUseCase.execute(
                fromLanguage = state.fromLanguage.language,
                fromText = state.fromText,
                toLanguage = state.toLanguage.language
            )

            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isTranslating = false,
                            toText = result.data
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isTranslating = false,
                            error = (result.throwable as? TranslateException)?.error
                        )
                    }
                }
            }
        }
    }
}