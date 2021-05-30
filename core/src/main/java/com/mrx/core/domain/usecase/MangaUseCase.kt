package com.mrx.core.domain.usecase

import com.mrx.core.data.Resource
import com.mrx.core.domain.model.Manga
import kotlinx.coroutines.flow.Flow

interface MangaUseCase {
    fun getAllItems(): Flow<Resource<List<Manga>>>
    fun getFavoriteItems(): Flow<List<Manga>>
    fun setFavoriteItems(items: Manga, state: Boolean)
}