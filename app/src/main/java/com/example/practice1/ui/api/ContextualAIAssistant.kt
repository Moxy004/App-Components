package com.example.practice1.ui.api

import com.google.ai.client.generativeai.GenerativeModel

class ContextualAIAssistant{
    private val generativeModel = GenerativeModel(
        modelName = "gemma-3-27b-it",
        apiKey = "AIzaSyDlAxYHsFghdcDFmEvXlLh2MFTQ9SCAkCM"
    )

    suspend fun getInsight(
        systemInstructions: String,
        contextData: String,
        userPrompt: String
    ): String {
        val fullPrompt = """
            $systemInstructions
            
            AVAILABLE DATA CONTEXT:
            $contextData
            
            USER REQUEST
            $userPrompt
        """.trimIndent()

        return try {
            val response = generativeModel.generateContent(fullPrompt)
            response.text ?: "No response generated"
        } catch (e: Exception){
            "Error: ${e.localizedMessage}"
        }
    }
}