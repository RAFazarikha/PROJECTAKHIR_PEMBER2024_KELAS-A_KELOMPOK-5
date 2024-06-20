package com.example.projectakhir.roomDatabasePariwisata

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.modulempat.utils.AppExecutor
import com.example.projectakhir.retrofitUser.APIConfigUser
import com.example.projectakhir.retrofitUser.APIResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.Volatile

class repositoryKonten private constructor(private val dao: daoKonten, private val appExecutor: AppExecutor){


    fun getAllKonten(): LiveData<List<tabelKonten>> = dao.getAllKonten()

    fun insertKonten(konten: tabelKonten) {
        appExecutor.diskIO().execute { dao.insertKonten(konten) }
    }

    fun deleteKonten(konten: tabelKonten){
        appExecutor.diskIO().execute { dao.deleteKonten(konten) }
    }

    fun updateKonten(konten: tabelKonten){
        appExecutor.diskIO().execute { dao.updateKonten(konten) }
    }

    companion object{

        @Volatile
        private var instance: repositoryKonten? = null

        fun getInstance(
            dao: daoKonten,
            appExecutor: AppExecutor
        ): repositoryKonten =
            instance ?: synchronized(this){
                instance ?: repositoryKonten(dao, appExecutor)
            }.also { instance = it }


    }
}