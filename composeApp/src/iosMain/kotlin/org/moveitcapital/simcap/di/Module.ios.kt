package org.moveitcapital.simcap.di

import androidx.room.Room
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.network.ktor3.KtorNetworkFetcherFactory
import io.ktor.client.HttpClient
import org.koin.dsl.module
import org.moveitcapital.simcap.data.local.database.AppDatabase
import platform.Foundation.NSHomeDirectory

actual val nativeModule = module {
    single<AppDatabase> {
        Room.databaseBuilder<AppDatabase>(
            name = NSHomeDirectory() + "/app.db",
        ).build()
    }

    single {
        val httpClient: HttpClient = get()
        ImageLoader.Builder(PlatformContext.INSTANCE)
            .components {
                add(KtorNetworkFetcherFactory(httpClient))
            }
            .build()
            .also { loader ->
                SingletonImageLoader.setSafe { loader }
            }
    }
}