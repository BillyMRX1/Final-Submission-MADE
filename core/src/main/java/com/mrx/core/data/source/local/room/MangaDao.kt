package com.mrx.core.data.source.local.room

import androidx.room.*
import com.mrx.core.data.source.local.entity.MangaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {

    @Query("SELECT * FROM manga")
    fun getAllManga(): Flow<List<MangaEntity>>

    @Query("SELECT * FROM manga where isFavorite = 1")
    fun getFavoriteManga(): Flow<List<MangaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManga(tourism: List<MangaEntity>)

    @Update
    fun updateFavoriteManga(tourism: MangaEntity)

}