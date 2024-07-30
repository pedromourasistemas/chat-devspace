package com.pedromoura.chatdevspace.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pedromoura.chatdevspace.presentation.ViewModelFactory.LoginViewModelFactory
import com.pedromoura.chatdevspace.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(
    LocalContext.current)
), navController: NavController) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val savedUserId = remember { loginViewModel.userId }
    val savedUsername = remember { loginViewModel.username }
    val savedPassword = remember { loginViewModel.password }

    LaunchedEffect(Unit) {
        loginViewModel.loadCredentials()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "Tela de Login",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Usuário", modifier = Modifier.padding(bottom = 8.dp), textAlign = TextAlign.Start) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it  },
            label = { Text(text = "Senha", modifier = Modifier.padding(bottom = 8.dp), textAlign = TextAlign.Start) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(onClick = {
            loginViewModel.saveCredentials(username, password)
            navController.navigate(Screen.Chat.route)
        }) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            loginViewModel.clearCredentials()
            username = ""
            password = ""
        }) {
            Text("Clear Credentials")
        }

        if (savedUsername.isNotEmpty() && savedPassword.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Saved UserID: $savedUserId")
            Text("Saved Username: $savedUsername")
            Text("Saved Password: $savedPassword")
        }
    }
}

@Preview
@Composable
fun PreviewLogin() {
    val navController = rememberNavController()
    val fakeViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(
        LocalContext.current))
    LoginScreen(fakeViewModel, navController)
}