package com.nimai.dnevnikcitanja.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pisac")
data class Pisac(
    var imePisca: String,
    var biljeska: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var idPisca: Int = 0

    override fun toString(): String {
        return imePisca
    }
}
