package org.moveitcapital.simcap.domain.usecase

import org.moveitcapital.simcap.data.remote.request.LoginRequest
import org.moveitcapital.simcap.domain.repository.SessionRepository

class LoginUseCase(private val repository: SessionRepository) {
    suspend operator fun invoke(request: LoginRequest) = repository.login(request)
}