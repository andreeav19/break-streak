package com.example.breakstreak.data.dao

import androidx.room.*
import com.example.breakstreak.data.model.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Query("SELECT * FROM habits WHERE userId = :userId")
    fun getHabitsByUser(userId: String): Flow<List<Habit>>
}
