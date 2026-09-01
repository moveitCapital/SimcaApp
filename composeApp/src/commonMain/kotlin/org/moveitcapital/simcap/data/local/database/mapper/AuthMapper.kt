package org.moveitcapital.simcap.data.local.database.mapper

import org.moveitcapital.simcap.data.local.database.entity.AuthEntity
import org.moveitcapital.simcap.data.local.database.entity.ProviderEntity
import org.moveitcapital.simcap.data.local.database.entity.UserDTOEntity
import org.moveitcapital.simcap.data.local.database.entity.UserEntity
import org.moveitcapital.simcap.domain.model.Auth

fun Auth.toAuthEntity() = AuthEntity(
    token = token
)

fun Auth.toUserEntity(id: Int) = UserEntity(
    id = id,
    userId = this.user.userId,
    username = this.user.username,
    profileId = this.user.profileId
)

fun Auth.toUserDTOEntity(id: Int) = this.userDTO.map {
    UserDTOEntity(
        id = id,
        dtoId = it.id,
        company = it.company,
        process = it.process,
        role = it.role,
        name = it.name,
    )
}

fun Auth.toProviderEntity(id: Int) = this.providers.map {
    ProviderEntity(
        id = id,
        companyId = it.companyId,
        companyName = it.companyName
    )
}