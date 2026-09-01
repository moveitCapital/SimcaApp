package org.moveitcapital.simcap.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("usuario_id") var userId: Int = 0,
    @SerialName("estado_id") var stateId: Int = 0,
    @SerialName("usu_user") var username: String = "",
    var persona_id: Int = 0,
    var sede_id: Int = 0,
    var usu_usuario_creacion: Int = 0,
    var usu_fecha_creacion: String = "",
    var usu_usuario_modificacion: Int = 0,
    var usu_fecha_modificacion: String = "",
    var perso_id: Int = 0,
    @SerialName("usu_id") var profileId: Int = 0,
    var est_id: Int = 0,
)