package com.github.freekdb.automatictranslation.translate.endpoint

import com.github.freekdb.automatictranslation.translate.Translator
import com.github.freekdb.automatictranslation.translate.languages.LanguageCleaner

class TranslationEndpoint {
    fun handleTranslateRequest(translateRequest: TranslateRequest, translator: Translator): TranslateResponse =
        translator.translateTexts(
            translateRequest.sourceTexts,
            LanguageCleaner().clean(translateRequest.targetLanguages, translator)
        )
}


data class TranslateRequest(val sourceTexts: List<String>, val targetLanguages: List<String>)

data class TranslateResponse(val data: List<SourceTranslateResponse>)

data class SourceTranslateResponse(
    val sourceText: String, val detectedSourceLanguage: String,
    val translations: Map<String, String>, val errors: String
)
