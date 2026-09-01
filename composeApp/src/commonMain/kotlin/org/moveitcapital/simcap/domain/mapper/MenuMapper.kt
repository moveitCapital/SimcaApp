package org.moveitcapital.simcap.domain.mapper

import org.moveitcapital.simcap.data.remote.response.MenuResponse
import org.moveitcapital.simcap.domain.model.MenuModule

fun MenuResponse.toDomain(): MenuModule = MenuModule(
    id = this.id,
    name = this.name,
    url = this.url,
    urlIcon = this.urlIcon.takeUnless { it == "null" },
    urlAnalytics = this.urlAnalytics.takeUnless { it == "null" },
    rolId = this.rolId,
    permissionRun = this.permissionRun,
    permissionRead = this.permissionRead,
    permissionWrite = this.permissionWrite,
    permissionDelete = this.permissionDelete,
    subMenu = this.subMenu?.map { it.toDomain() }
)