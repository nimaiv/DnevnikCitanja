package com.nimai.dnevnikcitanja.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Lik", foreignKeys = [ForeignKey(entity = Knjiga::class,
                                                        parentColumns = ["idKnjige"],
                                                        childColumns = ["idKnjige"],
                                                        onDelete = ForeignKey.CASCADE)])
data class Lik(
    @ColumnInfo(index = true)
    val idKnjige: Int,
    var imeLika: String,
    var opisLika: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var idLika: Int = 0
}