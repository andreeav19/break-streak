package com.example.breakstreak.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class LocalUser(
    @PrimaryKey val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)
