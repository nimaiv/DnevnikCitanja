package com.nimai.dnevnikcitanja.model.daos

import androidx.room.*
import com.nimai.dnevnikcitanja.model.entities.Pisac
import kotlinx.coroutines.flow.Flow

@Dao
interface PisacDao {
    @Query("SELECT * FROM Pisac")
    fun getAll(): Flow<List<Pisac>>

    @Insert
    suspend fun insertPisac(pisac: Pisac) : Long

    @Delete
    suspend fun delete(pisac: Pisac)

    @Update
    suspend fun updatePisac(pisac: Pisac)

    @Query("SELECT * FROM Pisac WHERE idPisca = :idPisca LIMIT 1")
    fun getPisac(idPisca: Int) : Flow<Pisac>

    @Query("SELECT * FROM Pisac WHERE idPisca = :idPisca LIMIT 1")
    suspend fun getPisacSync(idPisca: Int) : Pisac

    @Query("UPDATE Pisac SET biljeska = :biljeska WHERE idPisca = :idPisca")
    suspend fun updateBiljeskaPisca(biljeska: String?, idPisca: Int)

    @Query("SELECT COUNT(idPisca) FROM Pisac")
    fun getPisacCount(): Flow<Int>
}