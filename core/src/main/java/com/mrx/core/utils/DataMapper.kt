package com.mrx.core.utils

import com.mrx.core.data.source.local.entity.MangaEntity
import com.mrx.core.data.source.remote.response.MangaItem
import com.mrx.core.domain.model.Manga

object DataMapper {
    fun mapResponsesToEntities(input: List<MangaItem>): List<MangaEntity> {
        val mangaList = ArrayList<MangaEntity>()
        input.map {
            val manga = MangaEntity(
                malId = it.malId,
                rank = it.rank,
                title = it.title,
                url = it.url,
                type = it.type,
                startDate = it.startDate,
                members = it.members,
                score = it.score,
                imageUrl = it.imageUrl,
                isFavorite = false
            )
            mangaList.add(manga)
        }
        return mangaList
    }

    fun mapEntitiesToDomain(input: List<MangaEntity>): List<Manga> =
        input.map {
            Manga(
                malId = it.malId,
                rank = it.rank,
                title = it.title,
                url = it.url,
                type = it.type,
                startDate = it.startDate,
                members = it.members,
                score = it.score,
                imageUrl = it.imageUrl,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Manga) = MangaEntity(
        malId = input.malId,
        rank = input.rank,
        title = input.title,
        url = input.url,
        type = input.type,
        startDate = input.startDate,
        members = input.members,
        score = input.score,
        imageUrl = input.imageUrl,
        isFavorite = input.isFavorite
    )
}