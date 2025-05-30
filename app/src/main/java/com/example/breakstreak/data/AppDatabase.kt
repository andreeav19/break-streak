package com.example.breakstreak.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.breakstreak.data.dao.UserDao
import com.example.breakstreak.data.model.LocalUser

@Database(entities = [LocalUser::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}