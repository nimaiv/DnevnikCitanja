package com.nimai.dnevnikcitanja.model.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nimai.dnevnikcitanja.model.entities.Kategorija
import kotlinx.coroutines.flow.Flow

@Dao
interface KategorijaDao {
    @Query("SELECT * FROM Kategorija")
    fun getAll(): Flow<List<Kategorija>>

    @Insert
    suspend fun insertKategorija(kategorija: Kategorija)

    @Delete
    suspend fun delete(kategorija: Kategorija)

    @Query("SELECT * FROM Kategorija WHERE idKategorije = :idKategorije")
    fun getKategorija(idKategorije: Int) : Flow<Kategorija>

    @Query("SELECT * FROM Kategorija WHERE Kategorija.idKategorije NOT IN (SELECT Kategorija.idKategorije FROM Kategorija JOIN Atribut ON Kategorija.idKategorije = Atribut.idKategorije JOIN AtributKnjiga ON Atribut.idAtributa = AtributKnjiga.idAtributa WHERE idKnjige = :idKnjige)")
    fun getNepridjeljeneKategorijeKnjige(idKnjige: Int) : Flow<List<Kategorija>>

    @Query("SELECT DISTINCT Kategorija.idKategorije, Kategorija.nazivKategorije FROM Kategorija JOIN Atribut ON Kategorija.idKategorije = Atribut.idKategorije JOIN AtributKnjiga ON Atribut.idAtributa = AtributKnjiga.idAtributa WHERE idKnjige = :idKnjige")
    fun getKategorijeKnjige(idKnjige: Int) : Flow<List<Kategorija>>

    @Query("SELECT COUNT(idKategorije) FROM Kategorija")
    fun getKategorijaCount() : Flow<Int>
}