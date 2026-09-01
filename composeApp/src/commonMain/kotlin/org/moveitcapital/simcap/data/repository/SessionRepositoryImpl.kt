package org.moveitcapital.simcap.data.repository

import org.moveitcapital.simcap.core.AppError
import org.moveitcapital.simcap.core.Exeptions
import org.moveitcapital.simcap.core.ResultHandler
import org.moveitcapital.simcap.data.local.cache.SessionCache
import org.moveitcapital.simcap.data.local.database.dao.AuthDao
import org.moveitcapital.simcap.data.local.database.mapper.toAuthEntity
import org.moveitcapital.simcap.data.local.database.mapper.toProviderEntity
import org.moveitcapital.simcap.data.local.database.mapper.toUserDTOEntity
import org.moveitcapital.simcap.data.local.database.mapper.toUserEntity
import org.moveitcapital.simcap.data.local.preferences.PreferencesDataStore
import org.moveitcapital.simcap.data.remote.request.LoginRequest
import org.moveitcapital.simcap.data.remote.service.SessionService
import org.moveitcapital.simcap.data.remote.request.MenuRequest
import org.moveitcapital.simcap.data.remote.request.UpdatePasswordRequest
import org.moveitcapital.simcap.domain.mapper.toDomain
import org.moveitcapital.simcap.domain.model.Auth
import org.moveitcapital.simcap.domain.model.MenuModule
import org.moveitcapital.simcap.domain.model.Profile
import org.moveitcapital.simcap.domain.repository.SessionRepository

class SessionRepositoryImpl(
    private val sessionService: SessionService,
    private val preferencesDataStore: PreferencesDataStore,
    private val sessionCache: SessionCache,
    private val authDao: AuthDao
) : SessionRepository {

    override fun getAuthentication() = sessionCache.getAuth()

    override fun getProviderSessionId() = sessionCache.getProviderSessionId()

    override suspend fun loadSession() {
        preferencesDataStore.getSessionId()?.let { sessionId ->
            val authEntity = authDao.getAuth(sessionId) ?: return
            val user = authDao.getUser(sessionId) ?: return
            val userDTOs = authDao.getUserDTOs(sessionId)
            val providers = authDao.getProviders(sessionId)
            val auth = authEntity.toDomain(user, userDTOs, providers)
            auth.token.let { sessionCache.updateAuth(auth) }
            val providerSessionId = preferencesDataStore.getProviderSessionId()
            providerSessionId?.let { sessionCache.updateProviderSessionId(it) }
        }
    }

    override suspend fun saveAuthentication(auth: Auth) {
        val authEntity = auth.toAuthEntity()
        val sessionId = authDao.insertAuth(authEntity).toInt()
        val userEntity = auth.toUserEntity(sessionId)
        authDao.insertUser(userEntity)
        val userDTOs = auth.toUserDTOEntity(sessionId)
        authDao.insertUserDTOs(userDTOs)
        val providers = auth.toProviderEntity(sessionId)
        authDao.insertProviders(providers)
        sessionCache.updateAuth(auth)
        preferencesDataStore.saveSessionId(sessionId)
    }

    override suspend fun saveProviderSelectedId(providerId: Int) {
        sessionCache.updateProviderSessionId(providerId)
        preferencesDataStore.saveProviderSessionId(providerId)
    }

    override suspend fun login(request: LoginRequest): ResultHandler<Auth> {
        return sessionService.login(request)
    }

    override suspend fun logout() {
        preferencesDataStore.removeSessionId()
        preferencesDataStore.removeProviderSessionId()
        sessionCache.clear()
        authDao.clearAuth()
        authDao.clearUser()
        authDao.clearUserDTOs()
        authDao.clearProviders()
    }

    override suspend fun recoverPassword(email: String) = sessionService.recoverPassword(email)

    override suspend fun getMenu(request: MenuRequest): ResultHandler<List<MenuModule>> {
        return sessionCache.getAuth()?.token?.let {
            sessionService.getMenu(it, request)
        } ?: ResultHandler.Failure(AppError.Custom(Exeptions.NO_TOKEN))
    }

    override suspend fun updatePassword(request: UpdatePasswordRequest): ResultHandler<String> {
        return sessionCache.getAuth()?.token?.let {
            sessionService.updatePassword(it, request)
        } ?: ResultHandler.Failure(AppError.Custom(Exeptions.NO_TOKEN))
    }

    override suspend fun getProfile(userId: Int, companyId: Int): ResultHandler<Profile> {
        return sessionCache.getAuth()?.token?.let {
            sessionService.getProfile(it, userId, companyId)
        } ?: ResultHandler.Failure(AppError.Custom(Exeptions.NO_TOKEN))
    }
}