package com.mrx.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MangaResponse(

    @field:SerializedName("top")
    val top: List<MangaItem>,

    )

data class MangaItem(

    @field:SerializedName("mal_id")
    val malId: Int,

    @field:SerializedName("rank")
    val rank: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("start_date")
    val startDate: String,

    @field:SerializedName("members")
    val members: Int,

    @field:SerializedName("score")
    val score: Double,

    @field:SerializedName("image_url")
    val imageUrl: String

)
