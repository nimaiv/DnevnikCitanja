package com.nimai.dnevnikcitanja.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nimai.dnevnikcitanja.model.DnevnikRepository
import com.nimai.dnevnikcitanja.model.entities.Pisac

class HelperViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: DnevnikRepository = DnevnikRepository(application)

    fun updateBljeskaPisca(biljeska: String, idPisca: Int) = repository.updateBiljeskaPisca(biljeska, idPisca)

    fun getPisac(idPisca: Int) = repository.getPisac(idPisca)

    fun getKnjigaCount() = repository.getKnjigaCount()

    fun getPisacCount() = repository.getPisacCount()

    fun getKnjizevnaVrstaCount() = repository.getKnjizevnaVrstaCount()

    fun getLikCount() = repository.getLikCount()

    fun getCitatCount() = repository.getCitatCount()

    fun getKategorijaCount() = repository.getKategorijaCount()
}