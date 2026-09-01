package org.moveitcapital.simcap.domain.mapper

import org.moveitcapital.simcap.core.AppError
import org.moveitcapital.simcap.core.AppResponse
import org.moveitcapital.simcap.data.remote.response.ProfileApiResponse
import org.moveitcapital.simcap.data.remote.response.ProfileResponse
import org.moveitcapital.simcap.domain.model.Profile

fun ProfileApiResponse.toDomain(): Profile {
    profile ?: throw AppError.Custom("perfil nulo")
    if (messageCode != 200) throw AppError.Http(AppResponse(messageCode, message))
    return profile.toDomain()
}

fun ProfileResponse.toDomain() = Profile(
    idCompany = idCompany,
    company = company,
    process = process,
    position = position,
    name = name,
    identification = identification,
    email = email,
    phoneNumber = phoneNumber
)