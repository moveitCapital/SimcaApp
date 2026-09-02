package org.moveitcapital.simcap.data.local.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import org.moveitcapital.simcap.data.local.database.dao.AuthDao
import org.moveitcapital.simcap.data.local.database.entity.AuthEntity
import org.moveitcapital.simcap.data.local.database.entity.ProviderEntity
import org.moveitcapital.simcap.data.local.database.entity.UserDTOEntity
import org.moveitcapital.simcap.data.local.database.entity.UserEntity

@Database(
    entities = [AuthEntity::class, UserEntity::class, UserDTOEntity::class, ProviderEntity::class],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authDao(): AuthDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}