package org.moveitcapital.simcap.domain.mapper

import org.moveitcapital.simcap.core.AppError
import org.moveitcapital.simcap.core.AppResponse
import org.moveitcapital.simcap.data.local.database.entity.AuthEntity
import org.moveitcapital.simcap.data.local.database.entity.ProviderEntity
import org.moveitcapital.simcap.data.local.database.entity.UserDTOEntity
import org.moveitcapital.simcap.data.local.database.entity.UserEntity
import org.moveitcapital.simcap.data.remote.response.AuthResponse
import org.moveitcapital.simcap.data.remote.response.ProviderResponse
import org.moveitcapital.simcap.data.remote.response.UserDTOResponse
import org.moveitcapital.simcap.data.remote.response.UserResponse
import org.moveitcapital.simcap.domain.model.Auth
import org.moveitcapital.simcap.domain.model.Provider
import org.moveitcapital.simcap.domain.model.User
import org.moveitcapital.simcap.domain.model.UserDTO

fun AuthEntity.toDomain(
    user: UserEntity,
    userDTOs: List<UserDTOEntity>,
    providers: List<ProviderEntity>,
) = Auth(
    token = token,
    user = user.toDomain(),
    userDTO = userDTOs.map { it.toDomain() },
    providers = providers.map { it.toDomain() }
)

fun UserEntity.toDomain() = User(userId, username, profileId)

fun UserDTOEntity.toDomain() = UserDTO(dtoId, company, process, role, name)

fun ProviderEntity.toDomain() = Provider(companyName, companyId)

fun AuthResponse.toDomain(): Auth {
    val user = user?.toDomain()
    val userDTO = userDTO?.map { it.toDomain() }.orEmpty()
    val providers = providerList?.map { it.toDomain() }.orEmpty()
    return if (tokenValue != null && user != null) {
        Auth(
            token = tokenValue.orEmpty(),
            user = user,
            userDTO = userDTO,
            providers = providers
        )
    } else throw IllegalArgumentException("Invalid AuthResponse: missing token or user")
}

fun UserResponse.toDomain() = User(userId, username, profileId)

fun UserDTOResponse.toDomain() = UserDTO(dto_id, company, process, role, name)

fun ProviderResponse.toDomain() = Provider(companyName.orEmpty(), companyId)