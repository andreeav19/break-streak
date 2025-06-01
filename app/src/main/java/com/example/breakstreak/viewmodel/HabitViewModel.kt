package com.example.breakstreak.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakstreak.data.dao.HabitDao
import com.example.breakstreak.data.model.Habit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val habitDao: HabitDao
) : ViewModel() {

    fun insertHabit(habit: Habit) {
        viewModelScope.launch {
            habitDao.insertHabit(habit)
        }
    }
}
