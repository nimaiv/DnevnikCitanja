package com.nimai.dnevnikcitanja.model.daos

import androidx.room.*
import com.nimai.dnevnikcitanja.model.entities.KnjizevnaVrsta
import kotlinx.coroutines.flow.Flow

@Dao
interface KnjizevnaVrstaDao {
    @Query("SELECT * FROM KnjizevnaVrsta")
    fun getAll(): Flow<List<KnjizevnaVrsta>>

    @Insert
    suspend fun insertKnjizevnaVrsta(knjizevnaVrsta: KnjizevnaVrsta)

    @Delete
    suspend fun delete(knjizevnaVrsta: KnjizevnaVrsta)

    @Query("SELECT * FROM KnjizevnaVrsta WHERE idKnjizevneVrste = :idKnjizevneVrste")
    fun getKnjizevnaVrsta(idKnjizevneVrste: Int) : Flow<KnjizevnaVrsta>

    @Update
    suspend fun updateKnjizevnaVrsta(knjizevnaVrsta: KnjizevnaVrsta)

    @Query("SELECT COUNT(idKnjizevneVrste) FROM KnjizevnaVrsta")
    fun getKnjizevnaVrstaCount() : Flow<Int>
}