package org.moveitcapital.simcap.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Int,
    var userId: Int,
    //var estado_id: Int = 0,
    var username: String,
    //var persona_id: Int = 0,
    //var sede_id: Int = 0,
    //var usu_usuario_creacion: Int = 0,
    //var usu_fecha_creacion: String = "",
    //var usu_usuario_modificacion: Int = 0,
    //var usu_fecha_modificacion: String = "",
    //var perso_id: Int = 0,
    var profileId: Int = 0,
    //var est_id: Int = 0,
)