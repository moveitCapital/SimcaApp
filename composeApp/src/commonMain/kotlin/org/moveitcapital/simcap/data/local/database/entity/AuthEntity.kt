package org.moveitcapital.simcap.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth")
data class AuthEntity(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    val token: String
)