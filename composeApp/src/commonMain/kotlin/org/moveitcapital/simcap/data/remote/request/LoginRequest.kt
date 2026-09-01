package org.moveitcapital.simcap.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    var userName: String,
    var password: String
)