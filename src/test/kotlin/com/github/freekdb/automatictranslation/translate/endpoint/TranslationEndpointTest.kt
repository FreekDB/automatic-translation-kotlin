package com.github.freekdb.automatictranslation.translate.endpoint

import com.github.freekdb.automatictranslation.translate.google.GoogleTranslator
import com.github.freekdb.automatictranslation.translate.google.TranslateRequest
import com.github.freekdb.automatictranslation.translate.utilities.TestUtilities
import org.junit.Assert
import org.junit.Test

class TranslationEndpointTest {
    @Test
    fun testTranslationEndpoint() {
        val sourceText = "This text will be ignored."
        val sourceLanguage = "en"
        val targetLanguage = "de"
        val translatedText = "A test translation."

        val googleTranslate =
            TestUtilities.createGoogleTranslateMock(sourceText, sourceLanguage, targetLanguage, translatedText)

        val translator = GoogleTranslator(googleTranslate)

        val translateResponse = TranslationEndpoint().handleTranslateRequest(
            TranslateRequest(listOf(sourceText), listOf(targetLanguage)),
            translator
        )

        Assert.assertEquals(1, translateResponse.data.size)
        Assert.assertEquals(sourceLanguage, translateResponse.data[0].detectedSourceLanguage)
        Assert.assertEquals(mapOf(targetLanguage to translatedText), translateResponse.data[0].translations)
    }
}