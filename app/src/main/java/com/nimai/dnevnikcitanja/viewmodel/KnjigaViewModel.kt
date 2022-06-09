package com.nimai.dnevnikcitanja.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.nimai.dnevnikcitanja.model.DnevnikRepository
import com.nimai.dnevnikcitanja.model.entities.Knjiga
import com.nimai.dnevnikcitanja.model.entities.KnjizevnaVrsta
import com.nimai.dnevnikcitanja.model.entities.Pisac

class KnjigaViewModel(application: Application, val idKnjige: Int) : AndroidViewModel(application)  {

    var idTrenutnogPisca: Int? = null

    private var repository: DnevnikRepository = DnevnikRepository(application)

    var knjiga: LiveData<Knjiga>?

    init {
        knjiga = repository.getKnjiga(idKnjige)
    }

    fun updateKnjiga(knjiga: Knjiga) = repository.updateKnjiga(knjiga)


    fun getPisac(knjiga: Knjiga): LiveData<Pisac>? = knjiga.idPisca?.let { repository.getPisac(it) }

    fun getKnjizevnaVrsta(knjiga: Knjiga): LiveData<KnjizevnaVrsta>? = knjiga.idKnjizevneVrste?.let { repository.getKnjizevnaVrsta(it) }

    fun getPisci() = repository.getPisci()

    fun addPisac(pisac: Pisac) = repository.addPisac(pisac)

    fun updatePisac(pisac: Pisac) = repository.updatePisac(pisac)

    fun updatePisacKnjige(idPisca: Int) {
        idTrenutnogPisca = idPisca
        repository.updatePisacKnjige(idPisca, idKnjige)
    }

    fun obrisiPisca(pisac: Pisac) = repository.deletePisac(pisac)

    fun getKnjizevneVrste() = repository.getKnjizevneVrste()

    fun updateKnjizevnaVrstaKnjige(idKnjizevneVrste: Int) = repository.updateKnjizevnaVrstaKnjige(idKnjizevneVrste, idKnjige)

    fun addKnjizevnaVrsta(knjizevnaVrsta: KnjizevnaVrsta) = repository.addKnjizevnaVrsta(knjizevnaVrsta)

    fun obrisiKnjizevnuVrstu(knjizevnaVrsta: KnjizevnaVrsta) = repository.deleteKnjizevnaVrsta(knjizevnaVrsta)

    fun updateKnjizevnaVrsta(knjizevnaVrsta: KnjizevnaVrsta) = repository.updateKnjizevnaVrsta(knjizevnaVrsta)

    fun updateKratkiSadrzajKnjige(kratkiSadrzaj: String?) = repository.updateKratkiSadrzajKnjige(kratkiSadrzaj, idKnjige)

    fun updateTemaDjela(temaDjela: String?) = repository.updateTemaDjela(temaDjela, idKnjige)

    fun updateProstorIVrijemeKnjige(prostorIVrijeme: String?) = repository.updateProstorIVrijemeKnjige(prostorIVrijeme, idKnjige)

    fun updateJezikIStilKnjige(jezikIStil: String?) = repository.updateJezikIStilKnjige(jezikIStil, idKnjige)

    fun getKategorijeKnjige() = repository.getKategorijeKnjige(idKnjige)

    fun getAtributiKnjigeByIdKategorije(idKategorije: Int) =
        repository.getAtributiKnjigeByIdKategorije(idKategorije, idKnjige)

    fun obrisiAtributeKategorijeIKnjige(idKategorije: Int) = repository.obrisiAtributeKategorijeIKnjige(idKnjige, idKategorije)

    fun getCitatiKnjige() = repository.getCitatiKnjige(idKnjige)

    fun getLikoviKnjige() = repository.getLikoviKnjige(idKnjige)
}