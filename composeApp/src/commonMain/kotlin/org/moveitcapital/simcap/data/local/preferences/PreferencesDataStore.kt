package org.moveitcapital.simcap.data.local.preferences

import com.russhwolf.settings.Settings

class PreferencesDataStore(
    private val settings: Settings
) {
    companion object {
        private const val KEY_SESSION_ID = "authentication"
        private const val KEY_PROVIDER_SESSION = "provider_session_id"
    }

    fun saveSessionId(id: Int) = settings.putInt(KEY_SESSION_ID, id)

    fun getSessionId(): Int? = settings.getIntOrNull(KEY_SESSION_ID)

    fun removeSessionId() = settings.remove(KEY_SESSION_ID)

    fun saveProviderSessionId(id: Int) = settings.putInt(KEY_PROVIDER_SESSION, id)

    fun getProviderSessionId(): Int? = settings.getIntOrNull(KEY_PROVIDER_SESSION)

    fun removeProviderSessionId() = settings.remove(KEY_PROVIDER_SESSION)
}