package com.github.freekdb.automatictranslation.translate.google

import com.google.cloud.translate.Translate
import com.google.cloud.translate.Translation
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class GoogleTranslationTest {
    @Test
    fun testGetSupportedLanguages() {
        val googleTranslation = GoogleTranslation(Mockito.mock(Translate::class.java))
        val languages = googleTranslation.getSupportedLanguages()
        Assert.assertTrue(languages.isNotEmpty())
    }

    @Test
    fun testTranslateTextsSuccess() {
        val sourceText = "This text will be ignored."
        val sourceLanguage = "en"
        val targetLanguage = "de"
        val translatedText = "A test translation."

        val translation = Mockito.mock(Translation::class.java)
        val googleTranslate = Mockito.mock(Translate::class.java)
        val translateOption = Translate.TranslateOption.targetLanguage(targetLanguage)
        Mockito.`when`(translation.sourceLanguage).thenReturn(sourceLanguage)
        Mockito.`when`(translation.translatedText).thenReturn(translatedText)
        Mockito.`when`(googleTranslate.translate(listOf(sourceText), translateOption)).thenReturn(listOf(translation))

        val translateResponse = GoogleTranslation(googleTranslate)
            .translateTexts(listOf(sourceText), listOf(targetLanguage))

        Assert.assertEquals(1, translateResponse.data.size)
        Assert.assertEquals(sourceLanguage, translateResponse.data[0].detectedSourceLanguage)
        Assert.assertEquals(mapOf(targetLanguage to translatedText), translateResponse.data[0].translations)
    }

    @Test
    fun testTranslateTextsException() {
        val sourceText1 = "Hi"
        val sourceText2 = "Bye"
        val targetLanguage1 = "en"
        val targetLanguage2 = "en-US"
        val exceptionCause1 = "Translation exception 1"
        val exceptionCause2 = "Translation exception 2"

        val googleTranslate = Mockito.mock(Translate::class.java)
        val translateOption1 = Translate.TranslateOption.targetLanguage(targetLanguage1)
        val translateOption2 = Translate.TranslateOption.targetLanguage(targetLanguage2)
        Mockito.`when`(googleTranslate.translate(listOf(sourceText1, sourceText2), translateOption1))
            .thenThrow(RuntimeException(exceptionCause1))
        Mockito.`when`(googleTranslate.translate(listOf(sourceText1, sourceText2), translateOption2))
            .thenThrow(RuntimeException(exceptionCause2))

        val translateResponse = GoogleTranslation(googleTranslate).translateTexts(
            listOf(sourceText1, sourceText2),
            listOf(targetLanguage1, targetLanguage2)
        )

        Assert.assertEquals("$exceptionCause1, $exceptionCause2", translateResponse.data[0].errors)
    }
}
