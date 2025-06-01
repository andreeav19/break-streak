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
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object CreateHabit : Screen("create_habit")

}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Register.route) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Screen.Home.route) },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(Screen.Login.route) },
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.CreateHabit.route) {
            HabitCreateScreen(
                userId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                onHabitCreated = { navController.popBackStack() }
            )
        }
    }
}