package com.example.translatorkmmed.translate.domain.translate

enum class TranslateErrorEnum {
    SERVICE_UNAVAILABLE,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class TranslateException(val error: TranslateErrorEnum): Exception(
     "An error occurred when translating: $error"
)