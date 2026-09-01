package org.moveitcapital.simcap.domain.repository

import org.moveitcapital.simcap.core.ResultHandler
import org.moveitcapital.simcap.data.remote.request.LoginRequest
import org.moveitcapital.simcap.data.remote.request.MenuRequest
import org.moveitcapital.simcap.data.remote.request.UpdatePasswordRequest
import org.moveitcapital.simcap.domain.model.Auth
import org.moveitcapital.simcap.domain.model.MenuModule
import org.moveitcapital.simcap.domain.model.Profile

interface SessionRepository {
    fun getAuthentication(): Auth?
    fun getProviderSessionId(): Int?
    suspend fun loadSession()
    suspend fun saveAuthentication(auth: Auth)
    suspend fun saveProviderSelectedId(providerId: Int)
    suspend fun login(request: LoginRequest): ResultHandler<Auth>
    suspend fun logout()
    suspend fun recoverPassword(email: String): ResultHandler<String>
    suspend fun getMenu(request: MenuRequest): ResultHandler<List<MenuModule>>
    suspend fun updatePassword(request: UpdatePasswordRequest): ResultHandler<String>
    suspend fun getProfile(
        userId: Int,
        companyId: Int
    ): ResultHandler<Profile>
}