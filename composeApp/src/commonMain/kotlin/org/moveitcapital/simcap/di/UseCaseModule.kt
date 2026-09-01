package org.moveitcapital.simcap.di

import org.koin.dsl.module
import org.moveitcapital.simcap.domain.usecase.RecoverPasswordUseCase
import org.moveitcapital.simcap.domain.usecase.GetAuthenticationUseCase
import org.moveitcapital.simcap.domain.usecase.GetMenuUseCase
import org.moveitcapital.simcap.domain.usecase.GetProfileUseCase
import org.moveitcapital.simcap.domain.usecase.LoadAndGetSessionUseCase
import org.moveitcapital.simcap.domain.usecase.LoginUseCase
import org.moveitcapital.simcap.domain.usecase.LogoutUseCase
import org.moveitcapital.simcap.domain.usecase.SaveSessionUseCase
import org.moveitcapital.simcap.domain.usecase.UpdatePasswordUseCase

val useCaseModule = module {
    factory { LoginUseCase(get()) }
    factory { RecoverPasswordUseCase(get()) }
    factory { LoadAndGetSessionUseCase(get()) }
    factory { GetAuthenticationUseCase(get()) }
    factory { SaveSessionUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { GetMenuUseCase(get()) }
    factory { UpdatePasswordUseCase(get()) }
    factory { GetProfileUseCase(get()) }
}