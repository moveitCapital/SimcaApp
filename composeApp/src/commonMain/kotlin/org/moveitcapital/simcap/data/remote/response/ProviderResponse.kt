package org.moveitcapital.simcap.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProviderResponse(
    @SerialName("empresanombre") var companyName: String? = null,
    @SerialName("empresaid") var companyId: Int = 0,
)