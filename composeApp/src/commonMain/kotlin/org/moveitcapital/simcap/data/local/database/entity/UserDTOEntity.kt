package org.moveitcapital.simcap.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_dto")
data class UserDTOEntity(
    @PrimaryKey val id: Int,
    val dtoId: Int,
    val company: String,
    val process: String,
    val role: String,
    val name: String,
)