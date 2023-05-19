package com.example.translatorkmmed.translate.data.translate

import com.example.translatorkmmed.core.domain.launguage.Language
import com.example.translatorkmmed.translate.domain.translate.TranslateClient
import com.example.translatorkmmed.translate.domain.translate.TranslateErrorEnum
import com.example.translatorkmmed.translate.domain.translate.TranslateException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException

class KtorTranslateClient(private val httpClient: HttpClient) : TranslateClient {

    override suspend fun translate(fromLanguage: Language, fromText: String, toLanguage: Language): String {
        val result = try {
            httpClient.post {
                url("https://translate.pl-coding.com" + "/translate")
                contentType(ContentType.Application.Json)
                setBody(TranslateDto(textToTranslate = fromText, sourceLanguageCode = fromLanguage.langCode, targetLanguageCode = toLanguage.langCode))
            }
        } catch (e: IOException) {
            throw TranslateException(TranslateErrorEnum.SERVICE_UNAVAILABLE)
        }

        when (result.status.value) {
            in 200..299 -> Unit
            500 -> throw TranslateException(TranslateErrorEnum.SERVER_ERROR)
            in 400..499 -> throw TranslateException(TranslateErrorEnum.CLIENT_ERROR)
            else -> throw TranslateException(TranslateErrorEnum.UNKNOWN_ERROR)
        }

        return try {
            result.body<TranslatedDto>().translatedText
        } catch (e: Exception) {
            throw TranslateException(TranslateErrorEnum.SERVER_ERROR)
        }
    }
}