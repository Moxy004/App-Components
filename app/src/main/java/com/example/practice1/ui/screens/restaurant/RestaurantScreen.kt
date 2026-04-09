package com.example.practice1.ui.screens.restaurant

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practice1.local.AiRecommendation

/**
 * A standalone, drop-in component.
 * Your team can just call AiSuggestionsComponent() anywhere in their dashboard!
 */
@Composable
fun AiSuggestionsComponent(
    modifier: Modifier = Modifier,
    viewModel: BurgerViewModel = viewModel()
) {
    val recommendations by viewModel.recommendations.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()

    // We wrap everything in a Column so it stacks cleanly
    Column(modifier = modifier.fillMaxWidth()) {

        // 1. The Header
        Text(
            text = "AI Suggestions",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        // 2. The Generate Button
        Button(
            onClick = { viewModel.generateRecommendations() },
            enabled = !isGenerating,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isGenerating) "Cooking up an idea..." else "Generate Personalized Suggestion")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. The Horizontal List (LazyRow)
        if (recommendations.isEmpty()) {
            Text(
                text = "No suggestions yet. Tap the button above!",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp), // Spacing between cards
                modifier = Modifier.fillMaxWidth()
            ) {
                // Loop through our list and render a card for each
                items(recommendations) { recommendation ->
                    AiRecommendationCard(recommendation)
                }
            }
        }
    }
}

/**
 * The individual card UI for the horizontal list
 */
@Composable
fun AiRecommendationCard(recommendation: AiRecommendation) {
    Card(
        modifier = Modifier
            .width(260.dp) // Fixed width so they look like horizontal scrolling cards
            .wrapContentHeight(), // Height adjusts to fit the text
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Title
            Text(
                text = recommendation.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Description
            Text(
                text = recommendation.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}