package com.besha.shinobihub.features.login.presentaion.screen

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.besha.shinobihub.R
import com.besha.shinobihub.appcore.navigation.ScreenResources
import com.besha.shinobihub.features.login.data.model.login.LoginRequest
import com.besha.shinobihub.features.login.data.model.session.SessionRequest
import com.besha.shinobihub.features.login.presentaion.viewmodel.LoginActions
import com.besha.shinobihub.features.login.presentaion.viewmodel.LoginViewModel
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(rootController: NavController) {
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val state by loginViewModel.viewStates.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        loginViewModel.executeAction(LoginActions.GetRequestToken)
        loginViewModel.executeAction(LoginActions.Logout)
    }

    LaunchedEffect(
        state.loginResponse,
    ) {
        if (state.loginResponse.data?.success == true) {
            loginViewModel.executeAction(
                LoginActions.CreateSession(
                    SessionRequest(
                        state.requestToken.data?.request_token!!,
                    ),
                ),
            )
            Toast.makeText(context, "login successful", Toast.LENGTH_SHORT).show()
        } else if (state.loginResponse.errorThrowable != null) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")
            val expiryTime = ZonedDateTime.parse(state.requestToken.data?.expires_at, formatter)

            // Get the current time in UTC
            val now = ZonedDateTime.now(ZoneId.of("UTC"))

            if (now.isAfter(expiryTime)) {
                loginViewModel.executeAction(LoginActions.GetRequestToken)
                Toast.makeText(context, "token expired try again", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(state.sessionId.data) {
        if (state.sessionId.data != null) {
            loginViewModel.executeAction(LoginActions.SaveSessionId(state.sessionId.data!!))
            rootController.navigate(ScreenResources.MainScreeRoute()) {
                popUpTo(ScreenResources.AuthScreenRoute) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.Black),
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
        )

        // Centered Card
        Card(
            modifier =
                Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 32.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xCC1E1E1E)),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            if (state.loginResponse.isLoading) {
                CircularProgressIndicator()
            } else {
                Column(
                    modifier =
                        Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Login",
                        color = Color.White,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(bottom = 16.dp),
                    )

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors =
                            OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.Gray,
                            ),
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        colors =
                            OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.Gray,
                            ),
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            val loginRequest =
                                LoginRequest(
                                    username = username,
                                    password = password,
                                    request_token = state.requestToken.data?.request_token ?: "",
                                )
                            loginViewModel.executeAction(LoginActions.Login(loginRequest))
                        },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    ) {
                        Text("Login", color = Color.Black)
                    }

                    TextButton(
                        onClick = {
                            val signupUrl = "https://www.themoviedb.org/signup"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(signupUrl))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.padding(top = 4.dp),
                    ) {
                        Text("Sign up on TMDb", color = Color.LightGray)
                    }

                    TextButton(
                        onClick = {
                            rootController.navigate(ScreenResources.MainScreeRoute()) {
                                popUpTo(ScreenResources.AuthScreenRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.padding(top = 8.dp),
                    ) {
                        Text("Continue as Guest", color = Color.LightGray)
                    }
                }
            }
        }
    }
}
