package com.github.freekdb.automatictranslation.translate.endpoint

import com.github.freekdb.automatictranslation.translate.Translator
import com.github.freekdb.automatictranslation.translate.google.TranslateRequest
import com.github.freekdb.automatictranslation.translate.google.TranslateResponse
import com.github.freekdb.automatictranslation.translate.languages.LanguageCleaner

class TranslationEndpoint {
    fun handleTranslateRequest(translateRequest: TranslateRequest, translator: Translator): TranslateResponse =
        translator.translateTexts(
            translateRequest.sourceTexts,
            LanguageCleaner().clean(translateRequest.targetLanguages, translator)
        )
}
