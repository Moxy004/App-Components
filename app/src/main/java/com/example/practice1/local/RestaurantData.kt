package com.example.practice1.local
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// ==========================================
// 1. DATA MODELS (Room Ready)
// ==========================================

// When ready for Room, add: @Entity(tableName = "recommendations")
data class AiRecommendation(
    // When ready for Room, add: @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String
)

data class MenuItem(
    val name: String,
    val description: String
)

// ==========================================
// 2. REPOSITORY INTERFACE (DAO Bridge)
// ==========================================
interface RestaurantRepository {
    // Returns a Flow, exactly like a Room @Query("SELECT * FROM recommendations")
    fun getSavedRecommendations(): Flow<List<AiRecommendation>>

    // Saves new AI ideas to the database
    suspend fun saveRecommendations(recommendations: List<AiRecommendation>)

    // Context data for the AI
    fun getMenu(): List<MenuItem>
    fun getPastOrders(): List<String>
}

// ==========================================
// 3. MOCK DATABASE (For the Codefest)
// ==========================================
class MockRestaurantRepository : RestaurantRepository {

    // This MutableStateFlow acts EXACTLY like a Room database Flow.
    // When you add data to it, the UI will automatically update!
    private val _savedRecs = MutableStateFlow<List<AiRecommendation>>(emptyList())
    private var nextId = 1 // Simulates Room's auto-generating IDs

    override fun getSavedRecommendations(): Flow<List<AiRecommendation>> {
        return _savedRecs.asStateFlow()
    }

    override suspend fun saveRecommendations(recommendations: List<AiRecommendation>) {
        // Assign fake database IDs and add the new items to our existing list
        val newItemsWithIds = recommendations.map { it.copy(id = nextId++) }
        _savedRecs.value = _savedRecs.value + newItemsWithIds
    }

    override fun getMenu(): List<MenuItem> {
        return listOf(
            MenuItem("Classic Smash", "Double beef, cheese, house sauce."),
            MenuItem("Spicy Inferno", "Beef, jalapenos, pepper jack, spicy mayo."),
            MenuItem("Truffle Fries", "Crispy fries with truffle oil and parmesan."),
            MenuItem("Oreo Shake", "Thick vanilla shake with crushed Oreos.")
        )
    }

    override fun getPastOrders(): List<String> {
        return listOf(
            "Ordered Spicy Inferno and Truffle Fries on Tuesday.",
            "Ordered Classic Smash and Oreo Shake on Friday."
        )
    }
}