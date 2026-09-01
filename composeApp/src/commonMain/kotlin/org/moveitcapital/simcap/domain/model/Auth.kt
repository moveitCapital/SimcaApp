package org.moveitcapital.simcap.domain.model

data class Auth(
    val token: String,
    val user: User,
    val userDTO: List<UserDTO>,
    val providers: List<Provider>
)

data class User(
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

data class UserDTO(
    val id: Int,
    val company: String,
    val process: String,
    val role: String,
    val name: String,
)

data class Provider(
    val companyName: String,
    val companyId: Int,
)