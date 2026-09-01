package org.moveitcapital.simcap.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePasswordRequest (
    val user: String,
    val oldPassword: String,
    val newPassword: String
)