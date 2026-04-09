package com.example.practice1.ui.screens.restaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice1.local.*
import com.example.practice1.ui.api.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONArray

class BurgerViewModel(
    // 1. Inject the repository so we can save and read from our "Database"
    private val repository: RestaurantRepository = MockRestaurantRepository(),
    private val aiAssistant: ContextualAIAssistant = ContextualAIAssistant()
) : ViewModel() {

    // 2. Automatically listen to the Database!
    val recommendations = repository.getSavedRecommendations()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val isGenerating = MutableStateFlow(false)

    fun generateRecommendations() {
        viewModelScope.launch {
            isGenerating.value = true

            // 3. Grab actual data from our "Database" to feed the AI
            val menuContext = repository.getMenu().joinToString { "${it.name}: ${it.description}" }
            val pastOrders = repository.getPastOrders().joinToString(" | ")

            // Tell Gemini exactly how to format the JSON
            val instructions = """
                You are a personal assistant for a Restaurant App. I want you to list ALL the items in the menu.
                You MUST return ONLY a valid JSON array of objects. Do not include markdown formatting or backticks.
                Format exactly like this:
                [
                  { "title": "Spicy Combo", "description": "A spicy burger with fries." },
                  { "title": "Sweet Treat", "description": "Classic burger and a shake." }
                ]
            """.trimIndent()

            // 4. Send the actual database context to the AI
            val promptContext = "Menu: $menuContext \nPast Orders: $pastOrders"
            val aiResponseText = aiAssistant.getInsight(instructions, promptContext, "Return the JSON Array now.")

            try {
                // --- BULLETPROOF JSON PARSER ---
                // Find where the JSON array actually starts and ends, ignoring conversational text
                val startIndex = aiResponseText.indexOf('[')
                val endIndex = aiResponseText.lastIndexOf(']')

                if (startIndex == -1 || endIndex == -1) {
                    throw Exception("Could not find a JSON array. AI Response was: $aiResponseText")
                }

                // Extract ONLY the array part
                val cleanJson = aiResponseText.substring(startIndex, endIndex + 1)

                // Now parse it safely!
                val jsonArray = JSONArray(cleanJson)
                val newList = mutableListOf<AiRecommendation>()

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val title = jsonObject.getString("title")
                    val description = jsonObject.getString("description")

                    // Create the object (id = 0 because the DB will auto-generate the real ID)
                    newList.add(AiRecommendation(id = 0, title = title, description = description))
                }

                // 5. SAVE TO DATABASE!
                repository.saveRecommendations(newList)

            } catch (e: Exception) {
                // If it fails, save the RAW AI text as a recommendation so you can see why it broke!
                val errorList = listOf(
                    AiRecommendation(
                        id = 0,
                        title = "Error Parsing JSON",
                        description = "Error: ${e.message}\n\nRaw Output from AI:\n$aiResponseText"
                    )
                )
                repository.saveRecommendations(errorList)
            }

            isGenerating.value = false
        }
    }
}