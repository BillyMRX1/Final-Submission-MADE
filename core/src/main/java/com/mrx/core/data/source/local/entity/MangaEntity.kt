package com.mrx.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga")
data class MangaEntity(
    @PrimaryKey
    val malId: Int,
    val rank: Int,
    val title: String,
    val url: String,
    val type: String,
    val startDate: String,
    val members: Int,
    val score: Double,
    val imageUrl: String,
    var isFavorite: Boolean = false
)