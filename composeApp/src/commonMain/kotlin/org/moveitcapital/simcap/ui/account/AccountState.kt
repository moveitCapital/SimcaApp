package org.moveitcapital.simcap.ui.account

import org.moveitcapital.simcap.domain.model.Profile
import org.moveitcapital.simcap.domain.model.User
import org.moveitcapital.simcap.domain.model.UserDTO

data class AccountState(
    val profile: Profile? = null,
)