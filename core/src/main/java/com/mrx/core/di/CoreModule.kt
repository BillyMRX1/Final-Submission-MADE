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
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
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