package com.github.freekdb.automatictranslation.translate.google

import org.junit.Assert
import org.junit.Test

class GoogleTranslateSupplierTest {
    @Test
    fun testGoogleTranslateSupplier() {
        Assert.assertNotNull(GoogleTranslateSupplier().getGoogleTranslate())
    }
}
