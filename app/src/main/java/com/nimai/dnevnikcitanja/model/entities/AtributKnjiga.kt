package com.nimai.dnevnikcitanja.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "AtributKnjiga", primaryKeys = ["idKnjige", "idAtributa"],
        foreignKeys = [ForeignKey(entity = Knjiga::class,
                                    parentColumns = ["idKnjige"],
                                    childColumns = ["idKnjige"],
                                    onDelete = ForeignKey.CASCADE),
                        ForeignKey(entity = Atribut::class,
                                    parentColumns = ["idAtributa"],
                                    childColumns = ["idAtributa"],
                                    onDelete = ForeignKey.CASCADE)
                        ])
data class AtributKnjiga(
    val idKnjige: Int,
    @ColumnInfo(index = true)
    val idAtributa: Int,
    var vrijednostAtributa: String? = "-"
)