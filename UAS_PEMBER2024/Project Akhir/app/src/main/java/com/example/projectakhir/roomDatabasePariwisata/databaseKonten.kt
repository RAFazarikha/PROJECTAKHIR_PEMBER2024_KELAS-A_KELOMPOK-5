package com.example.projectakhir.roomDatabasePariwisata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [tabelKonten::class], version = 1)

@TypeConverters(konverter::class)
abstract class databaseKonten : RoomDatabase() {
    abstract fun kontenDao() : daoKonten

    companion object{
        @Volatile
        private var INSTANCE: databaseKonten? = null

        @JvmStatic
        fun getDatabase(context: Context): databaseKonten{
            if(INSTANCE == null){
                synchronized(databaseKonten::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        databaseKonten::class.java, "app_database"
                    )
                        .build()
                }
            }
            return INSTANCE as databaseKonten
        }
    }
}