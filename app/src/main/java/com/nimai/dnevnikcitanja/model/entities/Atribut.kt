package com.nimai.dnevnikcitanja.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Atribut", foreignKeys = [ForeignKey(entity = Kategorija::class,
                                                            parentColumns = ["idKategorije"],
                                                            childColumns = ["idKategorije"],
                                                            onDelete = ForeignKey.CASCADE)])
data class Atribut(
    @ColumnInfo(index = true)
    val idKategorije: Int,
    var nazivAtributa: String
) {
    @PrimaryKey(autoGenerate = true)
    var idAtributa: Int = 0
}