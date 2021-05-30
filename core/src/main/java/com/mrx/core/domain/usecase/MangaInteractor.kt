package com.mrx.core.domain.usecase

import com.mrx.core.domain.model.Manga
import com.mrx.core.domain.repository.IMangaRepository

class MangaInteractor(private val mangaRepository: IMangaRepository) : MangaUseCase {
    override fun getAllItems() = mangaRepository.getAllManga()

    override fun getFavoriteItems() = mangaRepository.getFavoriteManga()

    override fun setFavoriteItems(items: Manga, state: Boolean) =
        mangaRepository.setFavoriteManga(items, state)
}