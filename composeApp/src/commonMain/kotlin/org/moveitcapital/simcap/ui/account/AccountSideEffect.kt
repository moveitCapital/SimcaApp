package org.moveitcapital.simcap.ui.account

sealed interface AccountSideEffect {
    data class ShowMessage(val message: String) : AccountSideEffect
}