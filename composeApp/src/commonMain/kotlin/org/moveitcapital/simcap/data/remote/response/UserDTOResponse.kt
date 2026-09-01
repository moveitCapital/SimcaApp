package org.moveitcapital.simcap.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTOResponse(
    @SerialName("id")var dto_id: Int = 0,
    @SerialName("empresa") var company: String = "",
    @SerialName("proceso") var process: String = "",
    @SerialName("cargo") var role: String = "",
    @SerialName("nombre") var name: String = "",
)