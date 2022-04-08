package com.github.freekdb.automatictranslation.translate.google

import org.junit.Assert
import org.junit.Test

class GoogleConfigurationTest {
    @Test
    fun testGoogleConfiguration() {
        Assert.assertTrue(GoogleConfiguration().getConfigurationProperties().isNotEmpty())
    }
}
