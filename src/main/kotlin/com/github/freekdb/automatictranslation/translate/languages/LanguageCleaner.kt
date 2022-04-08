package com.github.freekdb.automatictranslation.translate.languages

import com.github.freekdb.automatictranslation.translate.Translator
import java.util.Locale

class LanguageCleaner {
    fun clean(targetLanguageRanges: List<String>, translator: Translator): List<String> {
        return targetLanguageRanges
            .map { languageRange -> findHighestSupportedLanguage(languageRange, translator) }
            .distinct()
    }

    private fun findHighestSupportedLanguage(languageRange: String, translator: Translator): String {
        // Note: the call to Locale.LanguageRange.parse() converts the language range to lower case.
        return Locale.LanguageRange.parse(languageRange)
            .asSequence()
            .map { it.range }
            .map { language -> translator.processLanguageException(language) }
            .map { targetLanguage -> stripDialects(targetLanguage) }
            .filter { language -> translator.isLanguageSupported(language) }
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
