package org.moveitcapital.simcap.data.local.cache

import kotlinx.coroutines.flow.MutableStateFlow
import org.moveitcapital.simcap.domain.model.Auth

class SessionCache{
    private val auth = MutableStateFlow<Auth?>(null)
    
    private val providerSessionId = MutableStateFlow<Int?>(null)
    
    fun getAuth() = auth.value
    
    fun getProviderSessionId() = providerSessionId.value

    suspend fun updateAuth(auth: Auth?) = this.auth.emit(auth)

    suspend fun updateProviderSessionId(id: Int?) = providerSessionId.emit(id)

    suspend fun clear() {
        auth.emit(null)
        providerSessionId.emit(null)
    }
}