package org.moveitcapital.simcap.domain.model

data class MenuModule(
    var id: Int = 0,
    var name: String = "",
    var url: String? = null,
    var urlIcon: String? = null,
    var rolId: Int? = null,
    var urlAnalytics: String? = null,
    var permissionRun: Int? = null,
    var permissionRead: Int? = null,
    var permissionWrite: Int? = null,
    var permissionDelete: Int? = null,
    var subMenu: List<MenuModule>? = null,
)