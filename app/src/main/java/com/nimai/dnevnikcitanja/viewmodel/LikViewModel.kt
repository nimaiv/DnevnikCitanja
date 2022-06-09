package com.nimai.dnevnikcitanja.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nimai.dnevnikcitanja.model.DnevnikRepository
import com.nimai.dnevnikcitanja.model.entities.Lik

class LikViewModel(application: Application, val idKnjige: Int = 0) : AndroidViewModel(application)  {

    private var repository: DnevnikRepository = DnevnikRepository(application)

    fun getLikoviKnjige() = repository.getLikoviKnjige(idKnjige)

    fun dodajLika(imeLika: String) = repository.addLik(Lik(idKnjige, imeLika))

    fun updateLik(idLika: Int, imeLika: String, opisLika: String) = repository.updateLik(idLika, imeLika, opisLika)

    fun obrisiLika(lik: Lik) = repository.deleteLik(lik)

    fun getLik(idLika: Int) = repository.getLik(idLika)
}