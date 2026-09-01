package org.moveitcapital.simcap.domain.usecase

import org.moveitcapital.simcap.core.ResultHandler
import org.moveitcapital.simcap.domain.repository.SessionRepository

class RecoverPasswordUseCase(private val repository: SessionRepository) {
    suspend operator fun invoke(email: String): ResultHandler<String> {
        return repository.recoverPassword(email)
    }
}