package com.example.breakstreak.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "milestones")
data class Milestone(
    @PrimaryKey val milestoneId: String,
    val name: String,
    val daysRequired: Int
)