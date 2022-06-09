package com.nimai.dnevnikcitanja.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nimai.dnevnikcitanja.model.DnevnikRepository
import com.nimai.dnevnikcitanja.model.entities.Citat

class CitatViewModel(application: Application, val idKnjige: Int = 0) : AndroidViewModel(application) {

    private var repository: DnevnikRepository = DnevnikRepository(application)

    fun getCitatiKnjige() = repository.getCitatiKnjige(idKnjige)

    fun addCitat(citat: String, brojStranice: Int) = repository.addCitat(Citat(idKnjige, citat, brojStranice))

    fun obrisiCitat(citat: Citat) = repository.deleteCitat(citat)

    fun getCitat(idCitata: Int) = repository.getCitat(idCitata)

    fun updateCitat(idCitata: Int, citat: String, brojStranice: Int, opisCitata: String) = repository.updateCitat(idCitata, citat, brojStranice, opisCitata)
}