package org.moveitcapital.simcap.domain.usecase

import org.moveitcapital.simcap.core.ResultHandler
import org.moveitcapital.simcap.data.remote.request.MenuRequest
import org.moveitcapital.simcap.data.remote.response.MenuResponse
import org.moveitcapital.simcap.domain.model.MenuModule
import org.moveitcapital.simcap.domain.repository.SessionRepository

class GetMenuUseCase(private val repository: SessionRepository) {
    suspend operator fun invoke(userId: Int, companyId: Int): ResultHandler<List<MenuModule>> {
        val request = MenuRequest(userId, companyId, 2)
        return repository.getMenu(request)
    }
}