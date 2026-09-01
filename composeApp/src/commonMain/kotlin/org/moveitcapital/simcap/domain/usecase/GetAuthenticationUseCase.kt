package org.moveitcapital.simcap.domain.usecase

import org.moveitcapital.simcap.domain.model.Auth
import org.moveitcapital.simcap.domain.repository.SessionRepository

class GetAuthenticationUseCase(private val repository: SessionRepository) {
    operator fun invoke(): Auth? {
        return repository.getAuthentication()
    }
}