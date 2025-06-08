package com.example.breakstreak.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.breakstreak.ui.habit.HabitCreateScreen
import com.example.breakstreak.ui.home.HomeScreen
import com.example.breakstreak.ui.login.LoginScreen
import com.example.breakstreak.ui.register.RegisterScreen
import com.google.firebase.auth.FirebaseAuth

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object CreateHabit : Screen("create_habit")
}

@Composable
fun NavGraph(navController: NavHostController) {
    val isUserLoggedIn = FirebaseAuth.getInstance().currentUser != null
    val startDestination = if (isUserLoggedIn) Screen.Home.route else Screen.Login.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToCreateHabit = { navController.navigate(Screen.CreateHabit.route) }
            )
        }
        composable(Screen.CreateHabit.route) {
            HabitCreateScreen(
                userId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                onHabitCreated = { navController.popBackStack() }
            )
        }
    }
}