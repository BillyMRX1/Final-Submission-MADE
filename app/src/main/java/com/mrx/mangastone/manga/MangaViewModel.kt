package com.mrx.mangastone.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mrx.core.domain.usecase.MangaUseCase

class MangaViewModel(mangaUseCase: MangaUseCase) : ViewModel() {
    val manga = mangaUseCase.getAllItems().asLiveData()
}

