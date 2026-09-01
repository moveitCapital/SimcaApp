package org.moveitcapital.simcap.domain.model

data class Profile(
    val idCompany: Int? = null,
    val company: String? = null,
    val process: String? = null,
    val position: String? = null,
    val name: String? = null,
    val identification: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null
)