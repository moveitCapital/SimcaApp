package org.moveitcapital.simcap.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuRequest(
    @SerialName("usu_id") var userId: Int,
    @SerialName("empre_id") var companyId: Int,
    @SerialName("appl_tipo") var appType: Int,
)