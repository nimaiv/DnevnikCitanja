package com.nimai.dnevnikcitanja.model

import android.app.Application
import androidx.lifecycle.asLiveData
import com.nimai.dnevnikcitanja.model.daos.*
import com.nimai.dnevnikcitanja.model.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DnevnikRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var knjigaDao: KnjigaDao?
    private var pisacDao: PisacDao?
    private var knjizevnaVrstaDao: KnjizevnaVrstaDao?
    private var likDao: LikDao?
    private var citatDao: CitatDao?
    private var kategorijaDao: KategorijaDao?
    private var atributDao: AtributDao?
    private var atributKnjigaDao: AtributKnjigaDao?


    init {
        val db = DnevnikDatabase.getDatabase(application)
        knjigaDao = db?.knjigaDao()
        pisacDao = db?.pisacDao()
        knjizevnaVrstaDao = db?.knjizevnaVrstaDao()
        likDao = db?.likDao()
        citatDao = db?.citatDao()
        kategorijaDao = db?.kategorijaDao()
        atributDao = db?.atributDao()
        atributKnjigaDao = db?.atributKnjigaDao()
    }

    /**
     * Funkcije za rad s tablicom Knjiga
     */
    fun getKnjige() = knjigaDao?.getAll()?.asLiveData()
    fun getKnjiga(idKnjige: Int) = knjigaDao?.getKnjiga(idKnjige)?.asLiveData()
    fun addKnjiga(knjiga: Knjiga) {
        launch {
            addKnjigaBG(knjiga)
        }
    }

    private suspend fun addKnjigaBG(knjiga: Knjiga) {
        withContext(Dispatchers.IO) {
            knjigaDao?.insertKnjiga(knjiga)
        }
    }

    fun deleteKnjiga(knjiga: Knjiga) {
        launch {
            deleteKnjigaBG(knjiga)
        }
    }

    private suspend fun deleteKnjigaBG(knjiga: Knjiga) {
        withContext(Dispatchers.IO) {
            knjigaDao?.delete(knjiga)
        }
    }

    fun updateKnjiga(knjiga: Knjiga) {
        launch {
            updateKnjigaBG(knjiga)
        }
    }

    private suspend fun updateKnjigaBG(knjiga: Knjiga) {
        withContext(Dispatchers.IO) {
            knjigaDao?.updateKnjiga(knjiga)
        }
    }

    fun updatePisacKnjige(idPisca: Int, idKnjige: Int) {
        launch {
            updatePisacKnjigeBG(idPisca, idKnjige)
        }
    }

    private suspend fun updatePisacKnjigeBG(idPisca: Int, idKnjige: Int) {
        withContext(Dispatchers.IO) {
            knjigaDao?.updatePisacKnjige(idPisca, idKnjige)
        }
    }

    fun updateKnjizevnaVrstaKnjige(idKnjizevneVrste: Int, idKnjige: Int) {
        launch {
            updateKnjizevnaVrstaKnjigeBG(idKnjizevneVrste, idKnjige)
        }
    }

    private suspend fun updateKnjizevnaVrstaKnjigeBG(idKnjizevneVrste: Int, idKnjige: Int) {
        withContext(Dispatchers.IO) {
            knjigaDao?.updateKnjizevnaVrstaKnjige(idKnjizevneVrste, idKnjige)
        }
    }

    fun updateKratkiSadrzajKnjige(kratkiSadrzaj: String?, idKnjige: Int) {
        launch {
            updateKratkiSadrzajKnjigeBG(kratkiSadrzaj, idKnjige)
        }
    }

    private suspend fun updateKratkiSadrzajKnjigeBG(kratkiSadrzaj: String?, idKnjige: Int) {
        withContext(Dispatchers.IO) {
            knjigaDao?.updateKratkiSadrzajKnjige(kratkiSadrzaj, idKnjige)
        }
    }

    fun updateTemaDjela(temaDjela: String?, idKnjige: Int) {
        launch {
            updateTemaDjelaBG(temaDjela, idKnjige)
        }
    }

    private suspend fun updateTemaDjelaBG(temaDjela: String?, idKnjige: Int) {
        withContext(Dispatchers.IO) {
            knjigaDao?.updateTemaDjela(temaDjela, idKnjige)
        }
    }

    fun updateProstorIVrijemeKnjige(prostorIVrijeme: String?, idKnjige: Int) {
        launch {
            updateProstorIVrijemeKnjigeBG(prostorIVrijeme, idKnjige)
        }
    }

    private suspend fun updateProstorIVrijemeKnjigeBG(prostorIVrijeme: String?, idKnjige: Int) {
        withContext(Dispatchers.IO) {
            knjigaDao?.updateProstorIVrijemeKnjige(prostorIVrijeme, idKnjige)
        }
    }

    fun updateJezikIStilKnjige(jezikIStil: String?, idKnjige: Int) {
        launch {
            updateJezikIStilKnjigeBG(jezikIStil, idKnjige)
        }
    }

    private suspend fun updateJezikIStilKnjigeBG(jezikIStil: String?, idKnjige: Int) {
        withContext(Dispatchers.IO) {
            knjigaDao?.updateJezikIStilKnjige(jezikIStil, idKnjige)
        }
    }

    /**
     * Funkcije za rad s tablicom Pisac
     */
    fun getPisci() = pisacDao?.getAll()?.asLiveData()
    fun getPisac(idPisca: Int) = pisacDao?.getPisac(idPisca)?.asLiveData()
    fun addPisac(pisac: Pisac) {
        launch {
            addPisacBG(pisac)
        }
    }

    private suspend fun addPisacBG(pisac: Pisac) {
        withContext(Dispatchers.IO) {
            pisacDao?.insertPisac(pisac)
        }
    }

    fun deletePisac(pisac: Pisac) {
        launch {
            deletePisacBG(pisac)
        }
    }

    private suspend fun deletePisacBG(pisac: Pisac) {
        withContext(Dispatchers.IO) {
            pisacDao?.delete(pisac)
        }
    }

    fun updatePisac(pisac: Pisac) {
        launch {
            updatePisacBG(pisac)
        }
    }

    private suspend fun updatePisacBG(pisac: Pisac) {
        withContext(Dispatchers.IO) {
            pisacDao?.updatePisac(pisac)
        }
    }

    fun updateBiljeskaPisca(biljeska: String?, idPisca: Int) {
        launch {
            updateBiljeskaPiscaBG(biljeska, idPisca)
        }
    }

    private suspend fun updateBiljeskaPiscaBG(biljeska: String?, idPisca: Int) {
        withContext(Dispatchers.IO) {
            pisacDao?.updateBiljeskaPisca(biljeska, idPisca)
        }
    }


    /**
     * Funkcije za rad s tablicom KnjizevnaVrsta
     */
    fun getKnjizevneVrste() = knjizevnaVrstaDao?.getAll()?.asLiveData()
    fun getKnjizevnaVrsta(idKnjizevneVrste: Int) =
        knjizevnaVrstaDao?.getKnjizevnaVrsta(idKnjizevneVrste)?.asLiveData()

    fun addKnjizevnaVrsta(knjizevnaVrsta: KnjizevnaVrsta) {
        launch {
            addKnjizevnaVrstaBG(knjizevnaVrsta)
        }
    }

    private suspend fun addKnjizevnaVrstaBG(knjizevnaVrsta: KnjizevnaVrsta) {
        withContext(Dispatchers.IO) {
            knjizevnaVrstaDao?.insertKnjizevnaVrsta(knjizevnaVrsta)
        }
    }

    fun deleteKnjizevnaVrsta(knjizevnaVrsta: KnjizevnaVrsta) {
        launch {
            deleteKnjizevnaVrstaBG(knjizevnaVrsta)
        }
    }

    private suspend fun deleteKnjizevnaVrstaBG(knjizevnaVrsta: KnjizevnaVrsta) {
        withContext(Dispatchers.IO) {
            knjizevnaVrstaDao?.delete(knjizevnaVrsta)
        }
    }

    fun updateKnjizevnaVrsta(knjizevnaVrsta: KnjizevnaVrsta) {
        launch {
            updateKnjizevnaVrstaBG(knjizevnaVrsta)
        }
    }

    private suspend fun updateKnjizevnaVrstaBG(knjizevnaVrsta: KnjizevnaVrsta) {
        withContext(Dispatchers.IO) {
            knjizevnaVrstaDao?.updateKnjizevnaVrsta(knjizevnaVrsta)
        }
    }

    /**
     * Funkcije za rad s tablicom Lik
     */
    fun getLikovi() = likDao?.getAll()?.asLiveData()
    fun getLik(idLika: Int) = likDao?.getLik(idLika)?.asLiveData()
    fun addLik(lik: Lik) {
        launch {
            addLikBG(lik)
        }
    }

    private suspend fun addLikBG(lik: Lik) {
        withContext(Dispatchers.IO) {
            likDao?.insertLik(lik)
        }
    }

    fun deleteLik(lik: Lik) {
        launch {
            deleteLikBG(lik)
        }
    }

    private suspend fun deleteLikBG(lik: Lik) {
        withContext(Dispatchers.IO) {
            likDao?.delete(lik)
        }
    }

    fun getLikoviKnjige(idKnjige: Int) = likDao?.getLikoviKnjige(idKnjige)?.asLiveData()

    fun updateLik(idLika: Int, imeLika: String, opisLika: String) {
        launch {
            updateLikBG(idLika, imeLika, opisLika)
        }
    }

    private suspend fun updateLikBG(idLika: Int, imeLika: String, opisLika: String) {
        withContext(Dispatchers.IO) {
            likDao?.updateLik(idLika, imeLika, opisLika)
        }
    }

    /**
     * Funkcije za rad s tablicom Citat
     */
    fun getCitati() = citatDao?.getAll()?.asLiveData()
    fun getCitat(idCitata: Int) = citatDao?.getCitat(idCitata)?.asLiveData()
    fun addCitat(citat: Citat) {
        launch {
            addCitatBG(citat)
        }
    }

    private suspend fun addCitatBG(citat: Citat) {
        withContext(Dispatchers.IO) {
            citatDao?.insertCitat(citat)
        }
    }

    fun deleteCitat(citat: Citat) {
        launch {
            deleteCitatBG(citat)
        }
    }

    private suspend fun deleteCitatBG(citat: Citat) {
        withContext(Dispatchers.IO) {
            citatDao?.delete(citat)
        }
    }

    fun getCitatiKnjige(idKnjige: Int) = citatDao?.getCitatiKnjige(idKnjige)?.asLiveData()

    fun updateCitat(idCitata: Int, citat: String, brojStranice: Int, opisCitata: String) {
        launch {
            updateCitatBG(idCitata, citat, brojStranice, opisCitata)
        }
    }

    private suspend fun updateCitatBG(
        idCitata: Int,
        citat: String,
        brojStranice: Int,
        opisCitata: String
    ) {
        withContext(Dispatchers.IO) {
            citatDao?.updateCitat(idCitata, citat, brojStranice, opisCitata)
        }
    }

    /**
     * Funkcije za rad s tablicom Kategorija
     */
    fun getKategorije() = kategorijaDao?.getAll()?.asLiveData()
    fun getKategorija(idKategorije: Int) = kategorijaDao?.getKategorija(idKategorije)?.asLiveData()
    fun addKategorija(kategorija: Kategorija) {
        launch {
            addKategorijaBG(kategorija)
        }
    }

    private suspend fun addKategorijaBG(kategorija: Kategorija) {
        withContext(Dispatchers.IO) {
            kategorijaDao?.insertKategorija(kategorija)
        }
    }

    fun deleteKategorija(kategorija: Kategorija) {
        launch {
            deleteKategorijaBG(kategorija)
        }
    }

    private suspend fun deleteKategorijaBG(kategorija: Kategorija) {
        withContext(Dispatchers.IO) {
            kategorijaDao?.delete(kategorija)
        }
    }

    fun getNepridjeljeneKategorijeKnjige(idKnjige: Int) =
        kategorijaDao?.getNepridjeljeneKategorijeKnjige(idKnjige)?.asLiveData()

    fun getKategorijeKnjige(idKnjige: Int) =
        kategorijaDao?.getKategorijeKnjige(idKnjige)?.asLiveData()

    /**
     * Funkcije za rad s tablicom Atribut
     */
    fun getAtributi() = atributDao?.getAll()?.asLiveData()
    fun getAtribut(idAtributa: Int) = atributDao?.getAtribut(idAtributa)?.asLiveData()
    fun getAtributByIdKategorije(idKategorije: Int) =
        atributDao?.getAtributByIdKategorije(idKategorije)?.asLiveData()

    fun addAtribut(atribut: Atribut) {
        launch {
            addAtributBG(atribut)
        }
    }

    private suspend fun addAtributBG(atribut: Atribut) {
        withContext(Dispatchers.IO) {
            atributDao?.insertAtribut(atribut)
        }
    }

    suspend fun addAtributWithReturn(atribut: Atribut): Int? {
        return atributDao?.insertAtribut(atribut)?.toInt()
    }

    fun deleteAtribut(atribut: Atribut) {
        launch {
            deleteAtributBG(atribut)
        }
    }

    private suspend fun deleteAtributBG(atribut: Atribut) {
        withContext(Dispatchers.IO) {
            atributDao?.delete(atribut)
        }
    }

    fun deleteAtributById(idAtributa: Int) {
        launch {
            deleteAtributByIdBG(idAtributa)
        }
    }

    private suspend fun deleteAtributByIdBG(idAtributa: Int) {
        withContext(Dispatchers.IO) {
            atributDao?.deleteAtributById(idAtributa)
        }
    }

    fun updateAtributNaziv(idAtributa: Int, nazivAtributa: String) {
        launch {
            updateAtributNazivBG(idAtributa, nazivAtributa)
        }
    }
    private suspend fun updateAtributNazivBG(idAtributa: Int, nazivAtributa: String) {
        withContext(Dispatchers.IO) {
            atributDao?.updateAtributNaziv(idAtributa, nazivAtributa)
        }
    }

    /**
     * Funkcije za rad s tablicom AtributKnjiga
     */
    fun getAtributKnjige() = atributKnjigaDao?.getAll()?.asLiveData()
    fun getAtributKnjiga(idKnjige: Int, idAtributa: Int) =
        atributKnjigaDao?.getAtribut(idKnjige, idAtributa)?.asLiveData()

    fun getAtributiKnjigeByIdKategorije(idKategorije: Int, idKnjige: Int) =
        atributKnjigaDao?.getAtributiKnjigeByIdKategorije(idKategorije, idKnjige)?.asLiveData()

    fun getAtributNazivIVrijednost(idAtributa: Int, idKnjige: Int) = atributKnjigaDao?.getAtributNazivIVrijednost(idAtributa, idKnjige)?.asLiveData()

    fun addAtributKnjiga(idKnjige: Int, idAtributa: Int) {
        launch {
            addAtributKnjigaBG(idKnjige, idAtributa)
        }
    }

    private suspend fun addAtributKnjigaBG(idKnjige: Int, idAtributa: Int) {
        withContext(Dispatchers.IO) {
            atributKnjigaDao?.insertAtributKnjiga(idKnjige, idAtributa)
        }
    }

    fun addAtributKnjiga(atributKnjiga: AtributKnjiga) {
        launch {
            addAtributKnjigaBG(atributKnjiga)
        }
    }

    private suspend fun addAtributKnjigaBG(atributKnjiga: AtributKnjiga) {
        withContext(Dispatchers.IO) {
            atributKnjigaDao?.insertAtributKnjiga(atributKnjiga)
        }
    }

    fun deleteAtributKnjiga(atributKnjiga: AtributKnjiga) {
        launch {
            deleteAtributKnjigaBG(atributKnjiga)
        }
    }

    private suspend fun deleteAtributKnjigaBG(atributKnjiga: AtributKnjiga) {
        withContext(Dispatchers.IO) {
            atributKnjigaDao?.delete(atributKnjiga)
        }
    }


    fun obrisiAtributeKategorijeIKnjige(idKnjige: Int, idKategorije: Int) {
        launch {
            obrisiAtributeKategorijeIKnjigeBG(idKnjige, idKategorije)
        }
    }

    private suspend fun obrisiAtributeKategorijeIKnjigeBG(idKnjige: Int, idKategorije: Int) {
        withContext(Dispatchers.IO) {
            atributKnjigaDao?.obrisiAtributeKategorijeIKnjige(idKnjige, idKategorije)
        }
    }

    fun updateAtributVrijednost(idKnjige: Int, idAtributa: Int, vrijednostAtributa: String) {
        launch {
            updateAtributVrijednostBG(idKnjige, idAtributa, vrijednostAtributa)
        }
    }
    private suspend fun updateAtributVrijednostBG(idKnjige: Int, idAtributa: Int, vrijednostAtributa: String) {
        withContext(Dispatchers.IO) {
            atributKnjigaDao?.updateAtributVrijednost(idKnjige, idAtributa, vrijednostAtributa)
        }
    }

    fun getKnjigeSKategorijomBezAtributa(idKategorije: Int, idAtributa: Int) = atributKnjigaDao?.getKnjigeSKategorijomBezAtributa(idKategorije, idAtributa)?.asLiveData()


    /**
     * Funkcije dodatak
     */
    fun getKnjigaJoinPisac() = knjigaDao?.getKnjigaJoinPisac()?.asLiveData()

    fun getKnjigaJoinPisac(chars: String) = knjigaDao?.getKnjigaJoinPisac(chars)?.asLiveData()

    fun getKnjigaCount() = knjigaDao?.getKnjigaCount()?.asLiveData()

    fun getPisacCount() = pisacDao?.getPisacCount()?.asLiveData()

    fun getKnjizevnaVrstaCount() = knjizevnaVrstaDao?.getKnjizevnaVrstaCount()?.asLiveData()

    fun getLikCount() = likDao?.getLikCount()?.asLiveData()

    fun getCitatCount() = citatDao?.getCitatCount()?.asLiveData()

    fun getKategorijaCount() = kategorijaDao?.getKategorijaCount()?.asLiveData()
}
