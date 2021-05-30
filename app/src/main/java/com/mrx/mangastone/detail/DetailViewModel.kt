package com.mrx.mangastone.detail

import androidx.lifecycle.ViewModel
import com.mrx.core.domain.model.Manga
import com.mrx.core.domain.usecase.MangaUseCase

class DetailViewModel(private val mangaUseCase: MangaUseCase) : ViewModel() {
    fun setFavoriteManga(manga: Manga, newStatus: Boolean) =
        mangaUseCase.setFavoriteItems(manga, newStatus)
}

