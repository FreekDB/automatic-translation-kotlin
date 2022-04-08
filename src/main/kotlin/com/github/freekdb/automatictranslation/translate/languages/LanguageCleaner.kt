package com.github.freekdb.automatictranslation.translate.languages

import com.github.freekdb.automatictranslation.translate.TranslationApi
import java.util.Locale

class LanguageCleaner {
    fun clean(targetLanguageRanges: List<String>, translation: TranslationApi): List<String> {
        return targetLanguageRanges
            .map { languageRange -> findHighestSupportedLanguage(languageRange, translation) }
            .distinct()
    }

    private fun findHighestSupportedLanguage(languageRange: String, translation: TranslationApi): String {
        // Note: the call to Locale.LanguageRange.parse() converts the language range to lower case.
        return Locale.LanguageRange.parse(languageRange)
            .asSequence()
            .map { it.range }
            .map { language -> translation.processLanguageException(language) }
            .map { targetLanguage -> stripDialects(targetLanguage) }
            .filter { language -> translation.isLanguageSupported(language) }
            .firstOrNull()
            ?: "en"
    }

    private fun stripDialects(targetLanguage: String): String {
        val exceptionLanguages = listOf("zh-cn", "zh-tw")
        return if (exceptionLanguages.contains(targetLanguage)) {
            targetLanguage
        } else {
            targetLanguage.substringBefore('-')
        }
    }
}
