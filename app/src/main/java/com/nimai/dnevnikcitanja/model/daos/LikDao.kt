package com.nimai.dnevnikcitanja.model.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nimai.dnevnikcitanja.model.entities.Lik
import kotlinx.coroutines.flow.Flow

@Dao
interface LikDao {
    @Query("SELECT * FROM Lik")
    fun getAll(): Flow<List<Lik>>

    @Insert
    suspend fun insertLik(lik: Lik)

    @Delete
    suspend fun delete(lik: Lik)

    @Query("SELECT * FROM Lik WHERE idLika = :idLika")
    fun getLik(idLika: Int) : Flow<Lik>

    @Query("SELECT * FROM LIK WHERE idKnjige = :idKnjige")
    fun getLikoviKnjige(idKnjige: Int) : Flow<List<Lik>>

    @Query("UPDATE Lik SET imeLika =:imeLika, opisLika = :opisLika WHERE idLika = :idLika")
    suspend fun updateLik(idLika: Int, imeLika: String, opisLika: String)

    @Query("SELECT COUNT(idLika) FROM Lik")
    fun getLikCount() : Flow<Int>
}