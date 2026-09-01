package org.moveitcapital.simcap.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    var tokenValue: String? = null,
    var messageCode: String? = null,
    @SerialName("mensaje") var message: String? = null,
    @SerialName("usuario") var user: UserResponse? = null,
    @SerialName("usuarioDTO") var userDTO: List<UserDTOResponse>? = null,
    @SerialName("listaProveedores") var providerList: List<ProviderResponse>? = null,
)