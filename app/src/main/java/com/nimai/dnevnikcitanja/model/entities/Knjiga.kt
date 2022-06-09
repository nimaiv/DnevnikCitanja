package com.nimai.dnevnikcitanja.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Knjiga", foreignKeys = [ForeignKey(entity = Pisac::class,
                                                        parentColumns = ["idPisca"],
                                                        childColumns = ["idPisca"],
                                                        onDelete = ForeignKey.SET_NULL),
                                            ForeignKey(entity = KnjizevnaVrsta::class,
                                                        parentColumns = ["idKnjizevneVrste"],
                                                        childColumns = ["idKnjizevneVrste"],
                                                        onDelete = ForeignKey.SET_NULL)
                                            ])
data class Knjiga(
    var naslovKnjige: String,
    @ColumnInfo(index = true) var idPisca: Int? = null,
    var brojStranica: Int? = null,
    @ColumnInfo(index = true) var idKnjizevneVrste: Int? = null,
    var kratkiSadrzaj: String? = null,
    var temaDjela: String? = null,
    var prostorIVrijeme: String? = null,
    var jezikIStil: String? = null
) {
    @PrimaryKey(autoGenerate = true) var idKnjige: Int = 0
}

