package com.mrx.mangastone.favorite.di

import com.mrx.mangastone.favorite.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favModule = module(override = true) {
    viewModel { FavoriteViewModel(get()) }
}