package com.mrx.mangastone.di

import com.mrx.core.domain.usecase.MangaInteractor
import com.mrx.core.domain.usecase.MangaUseCase
import com.mrx.mangastone.detail.DetailViewModel
import com.mrx.mangastone.manga.MangaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MangaUseCase> { MangaInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MangaViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}