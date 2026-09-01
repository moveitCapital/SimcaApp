package org.moveitcapital.simcap.domain.usecase

import org.moveitcapital.simcap.domain.repository.SessionRepository

class GetProfileUseCase(
    private val repository: SessionRepository
) {
    suspend operator fun invoke(
        userId: Int,
        companyId: Int
    ) = repository.getProfile(
        userId = userId,
        companyId = companyId
    )
}