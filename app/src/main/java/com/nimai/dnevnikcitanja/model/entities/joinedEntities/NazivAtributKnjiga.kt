package com.nimai.dnevnikcitanja.model.entities.joinedEntities

import androidx.room.Embedded
import com.nimai.dnevnikcitanja.model.entities.AtributKnjiga

data class NazivAtributKnjiga (
    @Embedded
    val atributKnjiga: AtributKnjiga,
    val nazivAtributa: String
)