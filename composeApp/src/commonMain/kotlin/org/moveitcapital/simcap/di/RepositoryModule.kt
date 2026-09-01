package org.moveitcapital.simcap.di

import org.koin.dsl.module
import org.moveitcapital.simcap.data.local.cache.SessionCache
import org.moveitcapital.simcap.data.repository.SessionRepositoryImpl
import org.moveitcapital.simcap.domain.repository.SessionRepository
import org.moveitcapital.simcap.domain.repository.SimcapRepository

val repositoryModule = module {
    single<SessionRepository> { SessionRepositoryImpl(get(), get(), get(), get()) }
}