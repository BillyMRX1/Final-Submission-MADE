package com.mrx.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Manga(
    val malId: Int,
    val rank: Int,
    val title: String,
    val url: String,
    val type: String,
    val startDate: String,
    val members: Int,
    val score: Double,
    val imageUrl: String,
    var isFavorite: Boolean
) : Parcelable