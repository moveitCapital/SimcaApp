package org.moveitcapital.simcap.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.moveitcapital.simcap.data.local.database.dao.AuthDao
import org.moveitcapital.simcap.data.local.database.entity.AuthEntity
import org.moveitcapital.simcap.data.local.database.entity.ProviderEntity
import org.moveitcapital.simcap.data.local.database.entity.UserDTOEntity
import org.moveitcapital.simcap.data.local.database.entity.UserEntity

@Database(
    entities = [AuthEntity::class, UserEntity::class, UserDTOEntity::class, ProviderEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authDao(): AuthDao
}