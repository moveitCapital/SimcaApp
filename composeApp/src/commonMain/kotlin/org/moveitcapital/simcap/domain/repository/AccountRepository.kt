package org.moveitcapital.simcap.domain.repository

import org.moveitcapital.simcap.core.ResultHandler
import org.moveitcapital.simcap.data.remote.request.UpdatePasswordRequest
import org.moveitcapital.simcap.domain.model.Profile

interface AccountRepository {
    suspend fun updatePassword(request: UpdatePasswordRequest): ResultHandler<String>
    suspend fun getProfile(
        userId: Int,
        companyId: Int
    ): ResultHandler<Profile>
}