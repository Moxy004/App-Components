package com.example.practice1.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily

val MoodieBlue = Color(0xFF3399FF)
val MoodieLightBlue = Color(0xFF66B2FF)
val MoodieGreen = Color(0xFFBADA18)
val DarkText = Color(0xFF333333)
val JuaFont = FontFamily.Cursive
val PatrickHandFont = FontFamily.Serif

@Composable
fun MoodieTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            isError = isError,
            shape = CircleShape, // <-- 1. Makes it a perfect pill shape
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 6.dp, shape = CircleShape, spotColor = Color.LightGray),

            // 2. Built-in placeholder! No more manual Box logic needed.
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray,
                    fontFamily = PatrickHandFont,
                    fontSize = 18.sp
                )
            },

            // 3. Custom Font
            textStyle = TextStyle(
                color = DarkText,
                fontFamily = PatrickHandFont,
                fontSize = 18.sp
            ),

            // 4. THE MAGIC: This removes the ugly default line at the bottom
            colors = TextFieldDefaults.colors(
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                errorContainerColor = White,
                focusedIndicatorColor = Color.Transparent,   // Hides the bottom line when typing
                unfocusedIndicatorColor = Color.Transparent, // Hides the bottom line normally
                errorIndicatorColor = Color.Transparent,     // Hides the bottom line on error
                cursorColor = DarkText
            )
        )

        // 5. Bonus: Actually displays the error message if you pass one!
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}