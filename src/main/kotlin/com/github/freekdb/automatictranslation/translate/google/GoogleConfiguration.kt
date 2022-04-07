package com.github.freekdb.automatictranslation.translate.google

import java.io.File
import java.io.IOException

class GoogleConfiguration {
    fun getConfigurationProperties(): String {
        val filePath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS")

        if (filePath != null && File(filePath).exists()) {
            try {
                return File(filePath).readLines().joinToString("\n")
            } catch (e: IOException) {
                throw IllegalStateException("Error while retrieving Google credentials from file.", e)
            }
        }

        throw IllegalStateException("Error while trying to retrieve Google credentials from file.")
    }
}
