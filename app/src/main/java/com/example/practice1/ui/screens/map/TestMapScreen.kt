package com.example.practice1.ui.screens.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practice1.ui.screens.restaurant.AiSuggestionsComponent

@Composable
fun TestScreen(){
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ){
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .clip(RoundedCornerShape(32.dp))
        ) {
            UserLocationMapScreen()
        }

        AiSuggestionsComponent()
    }
}

@Preview
@Composable
fun Screen(){
    TestScreen()
}
