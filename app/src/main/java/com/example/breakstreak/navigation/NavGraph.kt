package com.example.breakstreak.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.breakstreak.ui.login.LoginScreen
import com.example.breakstreak.ui.register.RegisterScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Register.route) {
        composable(Screen.Login.route) {
            LoginScreen(onNavigateToRegister = { navController.navigate(Screen.Register.route) })
        }
        composable(Screen.Register.route) {
            RegisterScreen(onRegisterSuccess = { navController.navigate(Screen.Login.route) })
        }
        composable(Screen.Home.route) {
//            HomeScreen()
        }
    }
}