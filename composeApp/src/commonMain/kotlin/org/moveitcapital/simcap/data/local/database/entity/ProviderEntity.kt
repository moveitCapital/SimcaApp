package org.moveitcapital.simcap.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "provider")
data class ProviderEntity(
    @PrimaryKey val id: Int,
    val companyId: Int,
    val companyName: String
)