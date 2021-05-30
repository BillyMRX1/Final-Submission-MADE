package com.mrx.core.di

import androidx.room.Room
import com.mrx.core.BuildConfig
import com.mrx.core.data.MangaRepository
import com.mrx.core.data.source.local.LocalDataSource
import com.mrx.core.data.source.local.room.MangaDatabase
import com.mrx.core.data.source.remote.RemoteDataSource
import com.mrx.core.data.source.remote.network.ApiService
import com.mrx.core.domain.repository.IMangaRepository
import com.mrx.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MangaDatabase>().mangaDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("manga".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            MangaDatabase::class.java, "Manga.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    val hostname = "api.jikan.moe"
    val certificatePinner = CertificatePinner.Builder()
        .add(hostname, "sha256/7WzOtAYFrxzTuC2KKPH0qCk4EB5tukcc+aX96BTmFWI=")
        .add(hostname, "sha256/jQJTbIh0grw0/1TkHSumWb+Fs0Ggogr621gT3PvPKG0=")
        .add(hostname, "sha256/Vjs8r4z+80wjNcr1YKepWQboSIRi63WsWXhIMN+eWys=")
        .build()
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMangaRepository> {
        MangaRepository(
            get(),
            get(),
            get()
        )
    }
}