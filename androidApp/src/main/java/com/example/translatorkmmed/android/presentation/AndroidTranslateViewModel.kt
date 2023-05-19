package com.example.translatorkmmed.android.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translatorkmmed.translate.domain.translate.TranslateUseCase
import com.example.translatorkmmed.translate.presentation.TranslateEvent
import com.example.translatorkmmed.translate.presentation.TranslateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(private val translateUseCase: TranslateUseCase) : ViewModel() {

    private val viewModel by lazy {
        TranslateViewModel(translateUseCase, viewModelScope)
    }

    val state = viewModel.state

    fun onEvent(event: TranslateEvent) {
        viewModel.onEvent(event)
    }
}