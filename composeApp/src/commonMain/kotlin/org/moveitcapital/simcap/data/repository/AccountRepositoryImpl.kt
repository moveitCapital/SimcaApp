package org.moveitcapital.simcap.data.repository

import org.moveitcapital.simcap.core.AppError
import org.moveitcapital.simcap.core.Exeptions
import org.moveitcapital.simcap.core.ResultHandler
import org.moveitcapital.simcap.data.local.cache.SessionCache
import org.moveitcapital.simcap.data.remote.request.UpdatePasswordRequest
import org.moveitcapital.simcap.data.remote.service.SessionService
import org.moveitcapital.simcap.domain.model.Profile
import org.moveitcapital.simcap.domain.repository.AccountRepository

class AccountRepositoryImpl(
    private val sessionCache: SessionCache,
    private val sessionService: SessionService
): AccountRepository {
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