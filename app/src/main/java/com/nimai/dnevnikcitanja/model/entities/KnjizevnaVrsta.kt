package com.nimai.dnevnikcitanja.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "KnjizevnaVrsta")
data class KnjizevnaVrsta(
    var knjizevnaVrsta: String
) {
    @PrimaryKey(autoGenerate = true)
    var idKnjizevneVrste: Int = 0
}