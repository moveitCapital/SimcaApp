package org.moveitcapital.simcap.domain.usecase

import org.moveitcapital.simcap.domain.model.Auth
import org.moveitcapital.simcap.domain.model.Provider
import org.moveitcapital.simcap.domain.repository.SessionRepository

class SaveSessionUseCase(private val repository: SessionRepository) {
    suspend operator fun invoke(
        auth: Auth,
        providerId: Provider?,
    ) {
        repository.saveAuthentication(auth)
        providerId?.let { repository.saveProviderSelectedId(it.companyId) }
    }
}