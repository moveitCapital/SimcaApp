package org.moveitcapital.simcap.core

import io.ktor.client.HttpClient

expect fun createHttpClient(environmentProvider: EnvironmentProvider): HttpClient