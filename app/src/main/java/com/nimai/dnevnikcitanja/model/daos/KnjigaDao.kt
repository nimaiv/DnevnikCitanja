package com.nimai.dnevnikcitanja.model.daos

import androidx.room.*
import com.nimai.dnevnikcitanja.model.entities.Knjiga
import com.nimai.dnevnikcitanja.model.entities.joinedEntities.KnjigaPisac
import kotlinx.coroutines.flow.Flow

@Dao
interface KnjigaDao {
    @Query("SELECT * FROM Knjiga")
    fun getAll(): Flow<List<Knjiga>>

    @Insert
    suspend fun insertKnjiga(knjiga: Knjiga)

    @Delete
    suspend fun delete(knjiga: Knjiga)

    @Query("SELECT * FROM Knjiga WHERE idKnjige = :idKnjige")
    fun getKnjiga(idKnjige: Int) : Flow<Knjiga>

    @Update
    suspend fun updateKnjiga(knjiga: Knjiga)

    @Query("SELECT * FROM Knjiga LEFT OUTER JOIN Pisac ON Knjiga.idPisca = Pisac.idPisca")
    fun getKnjigaJoinPisac(): Flow<List<KnjigaPisac>>

    @Query("SELECT * FROM Knjiga LEFT OUTER JOIN Pisac ON Knjiga.idPisca = Pisac.idPisca WHERE naslovKnjige LIKE '%' || :chars || '%'")
    fun getKnjigaJoinPisac(chars: String): Flow<List<KnjigaPisac>>

    @Query("UPDATE Knjiga SET idPisca = :idPisca WHERE idKnjige = :idKnjige")
    suspend fun updatePisacKnjige(idPisca: Int, idKnjige: Int)

    @Query("UPDATE Knjiga SET idKnjizevneVrste = :idKnjizevneVrste WHERE idKnjige = :idKnjige")
    suspend fun updateKnjizevnaVrstaKnjige(idKnjizevneVrste: Int, idKnjige: Int)

    @Query("UPDATE Knjiga SET kratkiSadrzaj = :kratkiSadrzaj WHERE idKnjige = :idKnjige")
    suspend fun updateKratkiSadrzajKnjige(kratkiSadrzaj: String?, idKnjige: Int)

    @Query("UPDATE Knjiga SET temaDjela = :temaDjela WHERE idKnjige = :idKnjige")
    suspend fun updateTemaDjela(temaDjela: String?, idKnjige: Int)

    @Query("UPDATE Knjiga SET prostorIVrijeme = :prostorIVrijeme WHERE idKnjige = :idKnjige")
    suspend fun updateProstorIVrijemeKnjige(prostorIVrijeme: String?, idKnjige: Int)

    @Query("UPDATE Knjiga SET jezikIStil = :jezikIStil WHERE idKnjige = :idKnjige")
    suspend fun updateJezikIStilKnjige(jezikIStil: String?, idKnjige: Int)

    @Query("SELECT COUNT(idKnjige) FROM Knjiga")
    fun getKnjigaCount() : Flow<Int>
}