package com.nimai.dnevnikcitanja.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Citat", foreignKeys = [ForeignKey(entity = Knjiga::class,
                                                        parentColumns = ["idKnjige"],
                                                        childColumns = ["idKnjige"],
                                                        onDelete = ForeignKey.CASCADE)])
data class Citat(
    @ColumnInfo(index = true)
    val idKnjige: Int,
    var citat: String,
    var brStranice: Int,
    var opisCitata: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var idCitata: Int = 0
}