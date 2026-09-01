package org.moveitcapital.simcap.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.moveitcapital.simcap.data.local.database.entity.AuthEntity
import org.moveitcapital.simcap.data.local.database.entity.ProviderEntity
import org.moveitcapital.simcap.data.local.database.entity.UserDTOEntity
import org.moveitcapital.simcap.data.local.database.entity.UserEntity

@Dao
interface AuthDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuth(auth: AuthEntity): Long
    
    @Query("SELECT * FROM auth WHERE id = :id")
    suspend fun getAuth(id: Int): AuthEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
    
    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUser(id: Int): UserEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDTOs(userDTOs: List<UserDTOEntity>)
    
    @Query("SELECT * FROM user_dto WHERE id = :id")
    suspend fun getUserDTOs(id: Int): List<UserDTOEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProviders(providers: List<ProviderEntity>)
    
    @Query("SELECT * FROM provider WHERE id = :id")
    suspend fun getProviders(id: Int): List<ProviderEntity>
    
    @Query("DELETE FROM auth")
    suspend fun clearAuth()
    
    @Query("DELETE FROM user")
    suspend fun clearUser()
    
    @Query("DELETE FROM user_dto")
    suspend fun clearUserDTOs()
    
    @Query("DELETE FROM provider")
    suspend fun clearProviders()

}