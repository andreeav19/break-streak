package com.example.breakstreak.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.breakstreak.navigation.Screen

@Composable
fun HomeScreen(onNavigateToCreateHabit: () -> Unit = {} ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to Home Screen")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            onNavigateToCreateHabit()
        }) {
            Text("Create New Habit")
        }
    }
}
