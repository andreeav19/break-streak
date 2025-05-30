package com.example.breakstreak.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.breakstreak.data.dao.UserDao
import com.example.breakstreak.data.model.Habit
import com.example.breakstreak.data.model.HabitLog
import com.example.breakstreak.data.model.HabitMilestone
import com.example.breakstreak.data.model.LocalUser
import com.example.breakstreak.data.model.Milestone
import com.example.breakstreak.util.Converter

@Database(entities = [
    LocalUser::class,
    Habit::class,
    HabitLog::class,
    Milestone::class,
    HabitMilestone::class
    ], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}