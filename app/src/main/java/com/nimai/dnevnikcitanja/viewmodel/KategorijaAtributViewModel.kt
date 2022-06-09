package com.nimai.dnevnikcitanja.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import com.nimai.dnevnikcitanja.model.DnevnikRepository
import com.nimai.dnevnikcitanja.model.entities.Atribut
import com.nimai.dnevnikcitanja.model.entities.AtributKnjiga
import com.nimai.dnevnikcitanja.model.entities.Kategorija
import com.nimai.dnevnikcitanja.view.KategorijaActivity
import kotlinx.coroutines.*

class KategorijaAtributViewModel(application: Application, val idKnjige: Int = 0) :
    AndroidViewModel(application) {

    private var repository: DnevnikRepository = DnevnikRepository(application)


    fun getNepridjeljeneKategorijeKnjige() = repository.getNepridjeljeneKategorijeKnjige(idKnjige)

    fun addKategorija(kategorija: Kategorija) = repository.addKategorija(kategorija)

    fun obrisiKategoriju(kategorija: Kategorija) = repository.deleteKategorija(kategorija)

    fun addAtribut(atribut: Atribut) = repository.addAtribut(atribut)

    fun getAtributByIdKategorije(idKategorije: Int) =
        repository.getAtributByIdKategorije(idKategorije)

    fun addAtributKnjiga(atributKnjiga: AtributKnjiga) = repository.addAtributKnjiga(atributKnjiga.idKnjige, atributKnjiga.idAtributa)

    fun addAtributKnjigaWithValue(atributKnjiga: AtributKnjiga) = repository.addAtributKnjiga(atributKnjiga)

    fun getAtributiKnjigeByIdKategorije(idKategorije: Int) =
        repository.getAtributiKnjigeByIdKategorije(idKategorije, idKnjige)

    fun obrisiAtribut(idAtributa: Int) = repository.deleteAtributById(idAtributa)

    fun dodajAtributIVrijednost(
        idKategorije: Int,
        nazivAtributa: String,
        vrijednostAtributa: String,
        context: KategorijaActivity
    ) {
        MainScope().launch {
            var idAtributa: Int? = null
            var ids: List<Int>
            MainScope().launch {
                withContext(Dispatchers.IO) {
                    idAtributa =
                        repository.addAtributWithReturn(Atribut(idKategorije, nazivAtributa))
                }
            }.join()
            idAtributa?.let { AtributKnjiga(idKnjige, it, vrijednostAtributa) }?.let {
                addAtributKnjigaWithValue(it)
            }

            if(idAtributa != null) {
                getKnjigeSKategorijomBezAtributa(idKategorije, idAtributa!!)?.observe(context, Observer {
                        for (idKnjige in it) {
                                addAtributKnjiga(AtributKnjiga(idKnjige, idAtributa!!))
                        }
                    })
            }
        }
    }

    fun updateAtributNazivIVrijednost(idAtributa: Int, nazivAtributa: String, vrijednostAtributa: String) {
        repository.updateAtributNaziv(idAtributa, nazivAtributa)
        repository.updateAtributVrijednost(idKnjige, idAtributa, vrijednostAtributa)
    }

    fun getAtributNazivIVrijednost(idAtributa: Int) =
        repository.getAtributNazivIVrijednost(idAtributa, idKnjige)

    fun getKnjigeSKategorijomBezAtributa(idKategorije: Int, idAtributa: Int) = repository.getKnjigeSKategorijomBezAtributa(idKategorije, idAtributa)
}