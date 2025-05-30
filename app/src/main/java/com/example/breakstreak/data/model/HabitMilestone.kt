package com.example.breakstreak.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.Date

@Entity(
    tableName = "habit_milestones",
    primaryKeys = ["habitId", "milestoneId"],
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["habitId"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Milestone::class,
            parentColumns = ["milestoneId"],
            childColumns = ["milestoneId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["habitId"]), Index(value = ["milestoneId"])]
)
data class HabitMilestone(
    val habitId: String,
    val milestoneId: String,
    val dateAchieved: Date
)