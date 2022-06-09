package com.nimai.dnevnikcitanja.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nimai.dnevnikcitanja.model.DnevnikRepository
import com.nimai.dnevnikcitanja.model.entities.Knjiga
import com.nimai.dnevnikcitanja.model.entities.Pisac

class PocetnaViewModel (application: Application) : AndroidViewModel(application) {

    private var repository: DnevnikRepository = DnevnikRepository(application)

    fun getKnjigeIPisce() = repository.getKnjigaJoinPisac()

    fun getKnjigeIPisce(chars: String) = repository.getKnjigaJoinPisac(chars)


    fun addKnjiga(naslov: String) {
        val knjiga = Knjiga(naslov)
        repository.addKnjiga(knjiga)
    }

    fun obrisiKnjigu(knjiga: Knjiga) = repository.deleteKnjiga(knjiga)



}