package org.moveitcapital.simcap.di

import io.ktor.client.HttpClient
import org.koin.dsl.module
import org.moveitcapital.simcap.core.Environment
import org.moveitcapital.simcap.core.EnvironmentProvider
import org.moveitcapital.simcap.core.createHttpClient

val appModule = module {
    single { EnvironmentProvider(Environment.PROD) }
    single<HttpClient> { createHttpClient(get()) }
}