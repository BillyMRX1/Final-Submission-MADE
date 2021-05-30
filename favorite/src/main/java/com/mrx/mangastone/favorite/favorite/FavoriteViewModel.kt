package com.mrx.mangastone.favorite.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mrx.core.domain.usecase.MangaUseCase

class FavoriteViewModel(mangaUseCase: MangaUseCase) : ViewModel() {
    val favoriteManga = mangaUseCase.getFavoriteItems().asLiveData()
}