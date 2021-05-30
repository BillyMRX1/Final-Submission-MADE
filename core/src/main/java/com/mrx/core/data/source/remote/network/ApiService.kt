package com.mrx.core.data.source.remote.network

import com.mrx.core.data.source.remote.response.MangaResponse
import retrofit2.http.GET

interface ApiService {
    @GET("top/manga")
    suspend fun getTopManga(): MangaResponse
}