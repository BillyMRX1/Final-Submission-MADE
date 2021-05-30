package com.mrx.core.data.source.remote

import android.util.Log
import com.mrx.core.data.source.remote.network.ApiResponse
import com.mrx.core.data.source.remote.network.ApiService
import com.mrx.core.data.source.remote.response.MangaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    fun getAllManga(): Flow<ApiResponse<List<MangaItem>>> {
        return flow {
            try {
                val resultData = apiService.getTopManga()
                val mangaList = resultData.top
                if (mangaList.isNotEmpty()) {
                    emit(ApiResponse.Success(mangaList))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}