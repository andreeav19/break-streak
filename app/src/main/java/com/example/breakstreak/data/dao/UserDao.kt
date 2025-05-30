package com.example.breakstreak.data.dao

import androidx.room.*
import com.example.breakstreak.data.model.LocalUser
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: LocalUser)

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUserById(userId: String): Flow<LocalUser?>

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<LocalUser>>

    @Delete
    suspend fun deleteUser(user: LocalUser)
}
