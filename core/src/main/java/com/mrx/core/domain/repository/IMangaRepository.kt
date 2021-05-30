package com.mrx.core.domain.repository

import com.mrx.core.data.Resource
import com.mrx.core.domain.model.Manga
import kotlinx.coroutines.flow.Flow

interface IMangaRepository {

    fun getAllManga(): Flow<Resource<List<Manga>>>

    fun getFavoriteManga(): Flow<List<Manga>>

    fun setFavoriteManga(manga: Manga, state: Boolean)

}