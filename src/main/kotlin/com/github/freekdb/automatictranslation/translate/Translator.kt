package com.github.freekdb.automatictranslation.translate

import com.github.freekdb.automatictranslation.translate.endpoint.TranslateResponse

val CHINESE_DIALECTS = mapOf(
    "zh-hk" to "zh-tw",  // Map Hong Kong to Chinese (traditional)
    "zh-mo" to "zh-tw",  // Map Macau to Chinese (traditional)
    "zh-sg" to "zh-cn",  // Map Singapore to Chinese (simplified)
    "zh" to "zh-cn"      // Map general to Chinese (simplified)
)

interface Translator {
    fun processLanguageException(language: String): String =
        CHINESE_DIALECTS.getOrDefault(language, language)

    fun isLanguageSupported(language: String): Boolean =
        getSupportedLanguages().any { it.equals(language, ignoreCase = true) }

    fun getSupportedLanguages(): List<String>
    fun translateTexts(sourceTexts: List<String>, targetLanguages: List<String>): TranslateResponse
}
