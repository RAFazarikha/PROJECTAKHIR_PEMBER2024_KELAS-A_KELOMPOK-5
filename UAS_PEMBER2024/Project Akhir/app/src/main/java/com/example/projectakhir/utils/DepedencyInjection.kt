package com.example.modulempat.utils

import android.content.Context
import com.example.projectakhir.retrofitUser.Repository
import com.example.projectakhir.roomDatabasePariwisata.databaseKonten
import com.example.projectakhir.roomDatabasePariwisata.repositoryKonten

object DepedencyInjection {
    fun provideRepository(context: Context): repositoryKonten {
        // Membuat instance dari AppDatabase
        val database = databaseKonten.getDatabase(context)
        // Membuat instance dari AppExecutors
        val appExecutors = AppExecutor()
        // Mendapatkan instance dari AppDao dari AppDatabase
        val dao = database.kontenDao()
        // Mendapatkan instance dari AppRepository menggunakan AppDao dan AppExecutors
        return repositoryKonten.getInstance(dao, appExecutors)
    }


}