package com.github.freekdb.automatictranslation.translate.languages

import com.github.freekdb.automatictranslation.translate.TranslationApi
import com.github.freekdb.automatictranslation.translate.google.TranslateResponse
import org.junit.Assert
import org.junit.Test

class LanguageCleanerTest {
    @Test
    fun testStripDialects() {
        val supportedLanguages = listOf("zh-cn", "zh-tw", "en", "nl", "fr", "de", "es", "it")
        val languageRangesWithDialects =
            listOf("zh-cn", "zh-tw", "en-us,en-ca;q=0.8", "nl-be", "fr-ca", "de-sw", "es-me", "it")
        val translator = MockTranslator()

        val languageCleaner = LanguageCleaner()

        Assert.assertEquals(supportedLanguages, languageCleaner.clean(translator.getSupportedLanguages(), translator))
        Assert.assertEquals(supportedLanguages, languageCleaner.clean(languageRangesWithDialects, translator))
    }

    @Test
    fun testFilterLanguages() {
        val supportedLanguages = listOf("en", "nl", "fr", "de", "es", "it")
        val expectedLanguages = listOf("en", "nl", "fr", "de", "es", "it", "zh-tw")
        val languageRangesWithUnsupported = supportedLanguages + listOf("vi;q=0.2", "cy,yo;q=0.6", "zu", "zh-MO")
        val translator = MockTranslator()

        val languageCleaner = LanguageCleaner()

        Assert.assertEquals(expectedLanguages, languageCleaner.clean(languageRangesWithUnsupported, translator))
    }


    @Test
    fun testFilterLanguagesWithInvalidTargetLanguageValues() {
        val translation = MockTranslator()
        val languageCleaner = LanguageCleaner()
        Assert.assertThrows(IllegalArgumentException::class.java) {
            languageCleaner.clean(listOf("aaa bbb ccc"), translation)
        }
    }
}


private class MockTranslator : TranslationApi {
    override fun getSupportedLanguages(): List<String> {
        return listOf("zh-CN", "zh-TW", "en", "nl", "fr", "de", "es", "it")
    }

    override fun translateTexts(sourceTexts: List<String>, targetLanguages: List<String>): TranslateResponse {
        return TranslateResponse(emptyList())
    }
}
