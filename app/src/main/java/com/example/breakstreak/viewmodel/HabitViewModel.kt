package com.example.breakstreak.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.example.breakstreak.data.dao.HabitDao
import com.example.breakstreak.data.model.Habit
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val habitDao: HabitDao
) : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    fun insertHabit(habit: Habit) {
        viewModelScope.launch {
            // insert to room
            habitDao.insertHabit(habit)

            // sync to firestore
            val userId = habit.userId
            val habitMap = mapOf(
                "habitId" to habit.habitId,
                "userId" to habit.userId,
                "name" to habit.name,
                "currentStreak" to habit.currentStreak,
                "maxStreak" to habit.maxStreak,
                "currentStreakStartDate" to habit.currentStreakStartDate.time,
                "maxStreakStartDate" to habit.maxStreakStartDate.time
            )

            firestore.collection("habits")
                .document(habit.habitId)
                .set(habitMap)
                .addOnSuccessListener {
                    Log.d("HabitViewModel", "Habit synced to Firestore")
                }
                .addOnFailureListener { e ->
                    Log.e("HabitViewModel", "Failed to sync habit: ${e.message}")
                }
        }
    }
}
