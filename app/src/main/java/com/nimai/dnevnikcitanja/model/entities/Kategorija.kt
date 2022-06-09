package com.nimai.dnevnikcitanja.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Kategorija")
data class Kategorija(
    var nazivKategorije: String
) {
    @PrimaryKey(autoGenerate = true)
    var idKategorije: Int = 0
}