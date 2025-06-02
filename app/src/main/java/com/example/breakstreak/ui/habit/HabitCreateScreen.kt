package com.example.breakstreak.ui.habit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.breakstreak.data.model.Habit
import com.example.breakstreak.viewmodel.HabitViewModel
import java.util.Date
import java.util.UUID


@Composable
fun HabitCreateScreen(
    userId: String,
    onHabitCreated: () -> Unit,
    viewModel: HabitViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var habitName by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Create New Habit", fontWeight = FontWeight.Bold, fontSize = 22.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = habitName,
            onValueChange = { habitName = it },
            label = { Text("Habit Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (habitName.isBlank()) {
                    error = "Habit name cannot be empty"
                    return@Button
                }

                isLoading = true
                val newHabit = Habit(
                    habitId = UUID.randomUUID().toString(),
                    userId = userId,
                    name = habitName.trim(),
                    currentStreak = 0,
                    maxStreak = 0,
                    currentStreakStartDate = Date(),
                    maxStreakStartDate = Date()
                )

                viewModel.insertHabit(newHabit)
                Toast.makeText(context, "Habit created!", Toast.LENGTH_SHORT).show()
                onHabitCreated()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text("Save Habit")
        }

        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = Color.Red)
        }
    }
}
