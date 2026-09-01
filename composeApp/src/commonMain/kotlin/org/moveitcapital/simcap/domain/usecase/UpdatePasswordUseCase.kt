package org.moveitcapital.simcap.domain.usecase

import org.moveitcapital.simcap.core.ResultHandler
import org.moveitcapital.simcap.data.remote.request.UpdatePasswordRequest
import org.moveitcapital.simcap.domain.repository.SessionRepository

class UpdatePasswordUseCase(
    private val repository: SessionRepository
) {
    suspend operator fun invoke(
        oldPassword: String,
        newPassword: String
    ): ResultHandler<String> {
        val user = repository.getAuthentication()?.user?.username.orEmpty()
        val request = UpdatePasswordRequest(user, oldPassword, newPassword)
        return repository.updatePassword(request)
    }
}