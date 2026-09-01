package org.moveitcapital.simcap.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuResponse(
    @SerialName("id") var id: Int = 0,
    @SerialName("nombre") var name: String = "",
    @SerialName("url") var url: String? = null,
    @SerialName("urlIcono") var urlIcon: String? = null,
    @SerialName("rol_id") var rolId: Int? = null,
    @SerialName("urlAnalitica") var urlAnalytics: String? = null,
    @SerialName("rolapperm_ejecutar") var permissionRun: Int? = null,
    @SerialName("rolapperm_lectura") var permissionRead: Int? = null,
    @SerialName("rolapperm_escritura") var permissionWrite: Int? = null,
    @SerialName("rolapperm_eliminar") var permissionDelete: Int? = null,
    @SerialName("hijo") var subMenu: List<MenuResponse>? = null,
)