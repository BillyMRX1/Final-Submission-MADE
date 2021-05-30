package com.mrx.core.data

import com.mrx.core.data.source.local.LocalDataSource
import com.mrx.core.data.source.remote.RemoteDataSource
import com.mrx.core.data.source.remote.network.ApiResponse
import com.mrx.core.data.source.remote.response.MangaItem
import com.mrx.core.domain.model.Manga
import com.mrx.core.domain.repository.IMangaRepository
import com.mrx.core.utils.AppExecutors
import com.mrx.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MangaRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMangaRepository {

    override fun getAllManga(): Flow<Resource<List<Manga>>> =
        object : NetworkBoundResource<List<Manga>, List<MangaItem>>() {
            override fun loadFromDB(): Flow<List<Manga>> {
                return localDataSource.getAllManga().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Manga>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MangaItem>>> =
                remoteDataSource.getAllManga()

            override suspend fun saveCallResult(data: List<MangaItem>) {
                val mangaList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertManga(mangaList)
            }
        }.asFlow()

    override fun getFavoriteManga(): Flow<List<Manga>> {
        return localDataSource.getFavoriteManga().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteManga(manga: Manga, state: Boolean) {
        val mangaEntity = DataMapper.mapDomainToEntity(manga)
        appExecutors.diskIO().execute { localDataSource.setFavoriteManga(mangaEntity, state) }
    }
}

