package org.moveitcapital.simcap.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileApiResponse(

    val messageCode: Int = 0,

    val message: String = "",

    @SerialName("perfilDTO") val profile: ProfileResponse? = null

)

@Serializable
class ProfileResponse(

    @SerialName("idEmpresa") val idCompany: Int? = null,

    @SerialName("empresa") val company: String? = null,

    @SerialName("proceso") val process: String? = null,

    @SerialName("cargo") val position: String? = null,

    @SerialName("nombre") val name: String? = null,

    @SerialName("cedula") val identification: String? = null,

    @SerialName("correo") val email: String? = null,

    @SerialName("celular") val phoneNumber: String? = null

)