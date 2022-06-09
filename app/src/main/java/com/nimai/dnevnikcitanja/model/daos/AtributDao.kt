package com.nimai.dnevnikcitanja.model.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nimai.dnevnikcitanja.model.entities.Atribut
import kotlinx.coroutines.flow.Flow

@Dao
interface AtributDao {
    @Query("SELECT * FROM Atribut")
    fun getAll(): Flow<List<Atribut>>

    @Insert
    suspend fun insertAtribut(atribut: Atribut) : Long

    @Delete
    suspend fun delete(atribut: Atribut)

    @Query("SELECT * FROM Atribut WHERE idAtributa = :idAtributa")
    fun getAtribut(idAtributa: Int) : Flow<Atribut>

    @Query("SELECT * FROM Atribut WHERE idKategorije = :idKategorije")
    fun getAtributByIdKategorije(idKategorije: Int) : Flow<List<Atribut>>

    @Query("DELETE FROM Atribut WHERE idAtributa = :idAtributa")
    suspend fun deleteAtributById(idAtributa: Int)

    @Query("UPDATE Atribut SET nazivAtributa = :nazivAtributa WHERE idAtributa = :idAtributa")
    suspend fun updateAtributNaziv(idAtributa: Int, nazivAtributa: String)


}