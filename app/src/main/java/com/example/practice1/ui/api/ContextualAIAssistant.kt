package com.example.practice1.ui.api

import com.google.ai.client.generativeai.GenerativeModel

class ContextualAIAssistant {

    private val generativeModel = GenerativeModel(
        modelName = "gemma-3-27b-it",
        apiKey = "AIzaSyDlAxYHsFghdcDFmEvXlLh2MFTQ9SCAkCM"
    )
    /**
     * A reusable function for ANY feature in your app.
     * @param systemInstructions: Tell the AI how to act (e.g., "You are a restaurant guide")
     * @param contextData: The data from your Room DB converted to a String
     * @param userPrompt: What the user actually typed
     */
    suspend fun getInsight(
        systemInstructions: String,
        contextData: String,
        userPrompt: String
    ): String {
        // Build a structured prompt so Gemini understands the assignment
        val fullPrompt = """
            $systemInstructions
            
            AVAILABLE DATA CONTEXT:
            $contextData
            
            USER REQUEST:
            $userPrompt
        """.trimIndent()

        return try {
            val response = generativeModel.generateContent(fullPrompt)
            response.text ?: "No response generated."
        } catch (e: Exception) {
            "Error: ${e.localizedMessage}"
        }
    }
}