package org.moveitcapital.simcap.ui.account

sealed interface AccountIntent {
    object Init : AccountIntent
}