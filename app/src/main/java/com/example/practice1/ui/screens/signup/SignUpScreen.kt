package com.example.practice1.ui.screens.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practice1.ui.components.MoodieTextField
import com.example.practice1.ui.screens.signup.SocialIconButton
import com.example.practice1.R

// Define the colors used in the design
val OliveGreen = Color(0xFF5B6B45)
val LightGrayText = Color(0xFFA0A0A0)
val BackgroundWhite = Color(0xFFFFFFFF)

@Composable
fun LoginScreen(
    onBackClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
    onLoginClicked: (String, String) -> Unit // Pass email and password back to your navigation/activity
) {
    // Local State Management (Replaces ViewModel)
    var email by rememberSaveable { mutableStateOf("") }
    var emailHasErrors by rememberSaveable { mutableStateOf(false) }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordHasErrors by rememberSaveable { mutableStateOf(false) }

    var rememberMe by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
    ) {
        // 1. Top Header Section
        HeaderSection(onBackClicked = onBackClicked)

        Spacer(modifier = Modifier.height(24.dp))

        // 2. Form Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            // Email Field using your MoodieTextField
            MoodieTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailHasErrors = false // hide error when user types
                },
                placeholder = "Email",
                isError = emailHasErrors,
                errorMessage = if (emailHasErrors) "Invalid email format" else null
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            MoodieTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordHasErrors = false // hide error when user types
                },
                placeholder = "Password",
                isError = passwordHasErrors,
                errorMessage = if (passwordHasErrors) "Password cannot be empty" else null
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Remember me & Forgot Password Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(checkedColor = OliveGreen)
                    )
                    Text(text = "Remember me", fontSize = 12.sp, color = Color.DarkGray)
                }

                Text(
                    text = "forgot password?",
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.clickable { /* Handle forgot password */ }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Login Button
            Button(
                onClick = {
                    // Simple validation example before triggering login
                    val isEmailValid = email.isNotBlank() && email.contains("@")
                    val isPasswordValid = password.isNotBlank()

                    emailHasErrors = !isEmailValid
                    passwordHasErrors = !isPasswordValid

                    if (isEmailValid && isPasswordValid) {
                        onLoginClicked(email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OliveGreen)
            ) {
                Text(text = "LOGIN", fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 3. Or login with Divider
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(modifier = Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
                Text(
                    text = "Or login with",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontSize = 12.sp,
                    color = LightGrayText
                )
                Divider(modifier = Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Social Login Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialIconButton(iconRes = R.drawable.onboarding_img)
                Spacer(modifier = Modifier.width(16.dp))
                SocialIconButton(iconRes = R.drawable.onboarding_img)
                Spacer(modifier = Modifier.width(16.dp))
                SocialIconButton(iconRes = R.drawable.onboarding_img)
            }
        }

        // Push footer to the bottom
        Spacer(modifier = Modifier.weight(1f))

        // 4. Footer Section (Register)
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = LightGrayText)) {
                    append("Already have an account? ")
                }
                withStyle(style = SpanStyle(color = OliveGreen, fontWeight = FontWeight.Bold)) {
                    append("Register")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .clickable { onRegisterClicked() },
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
    }
}

@Composable
fun HeaderSection(onBackClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(OliveGreen)
    ) {

        Image(
            painter = painterResource(id = R.drawable.onboarding_img),
            contentDescription = "Iced Coffee",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .scale(1.3f)
                .offset(x = 18.dp, y = -30.dp)
        )

        // Back Button
        IconButton(
            onClick = onBackClicked,
            modifier = Modifier.padding(top = 48.dp, start = 8.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
        }

        // Text Content
        Column(
            modifier = Modifier
                .padding(top = 100.dp, start = 24.dp, end = 140.dp)
        ) {
            Text(
                text = "Hi, Welcome back !",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod.",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 12.sp,
                lineHeight = 18.sp,
            )
        }
    }
}

@Composable
fun SocialIconButton(iconRes: Int) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable { /* Handle Social Login */ },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "Social Login",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview
@Composable
fun viewview(){
    MaterialTheme{
        Surface(modifier = Modifier.fillMaxSize()) {
            LoginScreen(onBackClicked = {},
                onLoginClicked = {email, password ->},
                onRegisterClicked = {})
        }
    }
}