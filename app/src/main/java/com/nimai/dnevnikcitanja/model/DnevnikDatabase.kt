package com.nimai.dnevnikcitanja.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nimai.dnevnikcitanja.model.daos.*
import com.nimai.dnevnikcitanja.model.entities.*

@Database(entities = [Knjiga::class, Pisac::class, KnjizevnaVrsta::class, Lik::class,
                    Citat::class, Kategorija::class, Atribut::class, AtributKnjiga::class], version = 1, exportSchema = false)

abstract class DnevnikDatabase : RoomDatabase() {
    abstract fun knjigaDao(): KnjigaDao
    abstract fun pisacDao(): PisacDao
    abstract fun knjizevnaVrstaDao(): KnjizevnaVrstaDao
    abstract fun likDao(): LikDao
    abstract fun citatDao(): CitatDao
    abstract fun kategorijaDao(): KategorijaDao
    abstract fun atributDao(): AtributDao
    abstract fun atributKnjigaDao(): AtributKnjigaDao

    companion object {

        @Volatile
        private var INSTANCE: DnevnikDatabase? = null

        fun getDatabase(context: Context): DnevnikDatabase? {
            if(INSTANCE == null) {
                synchronized(DnevnikDatabase::class.java) {
                    if(INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            DnevnikDatabase::class.java, "dnevnik_citanja.db"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}