package com.github.freekdb.automatictranslation.translate.utilities

import com.google.cloud.translate.Translate
import com.google.cloud.translate.Translation
import org.mockito.Mockito

class TestUtilities {
    companion object {
        fun createGoogleTranslateMock(sourceText: String, sourceLanguage: String, targetLanguage: String,
                                      translatedText: String): Translate {
            val translation = Mockito.mock(Translation::class.java)
            val googleTranslate = Mockito.mock(Translate::class.java)
            val translateOption = Translate.TranslateOption.targetLanguage(targetLanguage)

            Mockito.`when`(translation.sourceLanguage).thenReturn(sourceLanguage)
            Mockito.`when`(translation.translatedText).thenReturn(translatedText)
            Mockito.`when`(googleTranslate.translate(listOf(sourceText), translateOption)).thenReturn(listOf(translation))

            return googleTranslate
        }
    }
}
