package com.nimai.dnevnikcitanja.model.daos

import androidx.room.*
import com.nimai.dnevnikcitanja.model.entities.Citat
import kotlinx.coroutines.flow.Flow

@Dao
interface CitatDao {
    @Query("SELECT * FROM Citat")
    fun getAll(): Flow<List<Citat>>

    @Insert
    suspend fun insertCitat(citat: Citat)

    @Delete
    suspend fun delete(citat: Citat)

    @Query("SELECT * FROM Citat WHERE idCitata = :idCitata")
    fun getCitat(idCitata: Int) : Flow<Citat>

    @Query("SELECT * FROM Citat WHERE idKnjige = :idKnjige")
    fun getCitatiKnjige(idKnjige: Int) : Flow<List<Citat>>

    @Query("UPDATE Citat SET citat = :citat, brStranice = :brojStranice, opisCitata = :opisCitata WHERE idCitata = :idCitata")
    suspend fun updateCitat(idCitata: Int, citat: String, brojStranice: Int, opisCitata: String)

    @Query("SELECT COUNT(idCitata) FROM Citat")
    fun getCitatCount() : Flow<Int>
}