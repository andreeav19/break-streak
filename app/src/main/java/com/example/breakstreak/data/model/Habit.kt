package com.example.breakstreak.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "habits",
    foreignKeys = [
        ForeignKey(
            entity = LocalUser::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)
data class Habit(
    @PrimaryKey val habitId: String,
    val userId: String,
    val name: String,
    val maxStreak: Int,
    val maxStreakStartDate: Date,
    val currentStreak: Int,
    val currentStreakStartDate: Date
)