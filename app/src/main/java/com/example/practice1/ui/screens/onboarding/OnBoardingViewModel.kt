package com.example.practice1.ui.screens.onboarding

import android.content.Context
import androidx.core.content.edit

class PrefsHelper(context: Context){
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    var hasSeenOnboarding: Boolean
        get() = prefs.getBoolean("seen_onboarding", false)
        set(value) = prefs.edit{ putBoolean("seen_onboarding", value)}
}

data class Onboarding(
    val title: String,
    val description: String,
    val iconRes: Int
)

object OnboardingDataProvider{
    val pages = listOf(
        Onboarding(
            title = "Welcome to FitTrack",
            description = "Reach your health goals faster with personalized daily insights.",
            iconRes = android.R.drawable.ic_menu_compass
        ),
        Onboarding(
            title = "Smart AI Assistant",
            description = "Our intelligent assistant adapts to your screen, giving you personalized suggestions and automating your current task.",
            iconRes = android.R.drawable.ic_menu_sort_by_size
        ),
        Onboarding(
            title = "Ready to Start?",
            description = "Ready to transform? Create an account today and start your fitness journey.",
            iconRes = android.R.drawable.ic_menu_send
        )

    )
}