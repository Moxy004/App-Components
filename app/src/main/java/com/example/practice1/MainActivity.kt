package com.example.practice1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practice1.ui.screens.map.TestScreen
import com.example.practice1.ui.screens.onboarding.Onboarding
import com.example.practice1.ui.screens.onboarding.OnboardingScreen
import com.example.practice1.ui.screens.onboarding.PrefsHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = PrefsHelper(this)
        setContent {
            MaterialTheme{
                Surface(modifier = Modifier.fillMaxSize()) {
                    var currentScreen by remember {
                        mutableStateOf(if (prefs.hasSeenOnboarding) "login" else "onboarding")
                    }

                    if(currentScreen == "onboarding"){
                        OnboardingScreen(
                            onFinished = {
                                prefs.hasSeenOnboarding = true
                                currentScreen = "login"
                            }
                        )
                    } else {
                        TestScreen()
                    }
                }
            }
        }
    }
}

