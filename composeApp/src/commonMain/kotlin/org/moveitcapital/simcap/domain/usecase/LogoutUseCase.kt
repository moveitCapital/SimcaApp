package org.moveitcapital.simcap.domain.usecase

import org.moveitcapital.simcap.domain.repository.SessionRepository

class LogoutUseCase(private val repository: SessionRepository) {
    suspend operator fun invoke() {
        repository.logout()
    }
}