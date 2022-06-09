package com.nimai.dnevnikcitanja.model.entities.joinedEntities

import androidx.room.Embedded
import com.nimai.dnevnikcitanja.model.entities.Knjiga

data class KnjigaPisac(
    @Embedded
    val knjiga: Knjiga,
    val imePisca: String?,
    val biljeska: String?
)

