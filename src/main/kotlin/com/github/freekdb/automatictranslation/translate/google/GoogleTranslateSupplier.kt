package com.github.freekdb.automatictranslation.translate.google

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

class GoogleTranslateSupplier {
    fun getGoogleTranslate(): Translate {
        val configurationProperties = GoogleConfiguration().getConfigurationProperties()
        val configurationFromDatabase = configurationProperties.toByteArray(StandardCharsets.UTF_8)
        val configurationStream = ByteArrayInputStream(configurationFromDatabase)
        val googleCredentials = GoogleCredentials.fromStream(configurationStream)
        return TranslateOptions.newBuilder().setCredentials(googleCredentials).build().service
    }
}
