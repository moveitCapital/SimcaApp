package org.moveitcapital.simcap.domain.usecase

import org.moveitcapital.simcap.domain.model.Auth
import org.moveitcapital.simcap.domain.repository.SessionRepository

class LoadAndGetSessionUseCase (private val repository: SessionRepository) {
    suspend operator fun invoke(): Pair<Auth?, Int?> {
        repository.loadSession()
        val authentication = repository.getAuthentication()
        val providerId = repository.getProviderSessionId()
        return authentication to providerId
    }
}