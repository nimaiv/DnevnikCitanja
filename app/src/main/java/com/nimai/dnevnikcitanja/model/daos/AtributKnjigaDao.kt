package com.nimai.dnevnikcitanja.model.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nimai.dnevnikcitanja.model.entities.AtributKnjiga
import com.nimai.dnevnikcitanja.model.entities.joinedEntities.NazivAtributKnjiga
import kotlinx.coroutines.flow.Flow

@Dao
interface AtributKnjigaDao {
    @Query("SELECT * FROM AtributKnjiga")
    fun getAll(): Flow<List<AtributKnjiga>>

    @Query("INSERT INTO AtributKnjiga(idKnjige, idAtributa) SELECT :idKnjige, :idAtributa WHERE NOT EXISTS(SELECT 1 FROM AtributKnjiga WHERE idKnjige = :idKnjige AND idAtributa = :idAtributa)")
    suspend fun insertAtributKnjiga(idKnjige: Int, idAtributa: Int)

    @Insert
    suspend fun insertAtributKnjiga(atributKnjiga: AtributKnjiga)

    @Delete
    suspend fun delete(atributKnjiga: AtributKnjiga)

    @Query("SELECT * FROM AtributKnjiga WHERE idKnjige = :idKnjige AND idAtributa = :idAtributa")
    fun getAtribut(idKnjige: Int, idAtributa: Int) : Flow<AtributKnjiga>

    @Query("DELETE FROM AtributKnjiga WHERE idKnjige = :idKnjige AND idAtributa IN (SELECT idAtributa FROM Atribut JOIN Kategorija ON Atribut.idKategorije = Kategorija.idKategorije WHERE Atribut.idKategorije = :idKategorije)")
    suspend fun obrisiAtributeKategorijeIKnjige(idKnjige: Int, idKategorije: Int)

    @Query("SELECT idKnjige, AtributKnjiga.idAtributa, vrijednostAtributa, nazivAtributa FROM AtributKnjiga JOIN Atribut ON Atribut.idAtributa = AtributKnjiga.idAtributa WHERE idKnjige = :idKnjige AND idKategorije = :idKategorije")
    fun getAtributiKnjigeByIdKategorije(idKategorije: Int, idKnjige: Int) : Flow<List<NazivAtributKnjiga>>

    @Query("UPDATE AtributKnjiga SET vrijednostAtributa = :vrijednostAtributa WHERE idKnjige = :idKnjige AND idAtributa = :idAtributa")
    suspend fun updateAtributVrijednost(idKnjige: Int, idAtributa: Int, vrijednostAtributa: String)

    @Query("SELECT idKnjige, AtributKnjiga.idAtributa, vrijednostAtributa, nazivAtributa FROM AtributKnjiga JOIN Atribut using(idAtributa) WHERE idKnjige = :idKnjige AND AtributKnjiga.idAtributa = :idAtributa")
    fun getAtributNazivIVrijednost(idAtributa: Int, idKnjige: Int) : Flow<NazivAtributKnjiga>

    @Query("SELECT DISTINCT idKnjige FROM AtributKnjiga JOIN Atribut using(idAtributa) WHERE idKategorije = :idKategorije AND idAtributa <> :idAtributa")
    fun getKnjigeSKategorijomBezAtributa(idKategorije: Int, idAtributa: Int) : Flow<List<Int>>

}