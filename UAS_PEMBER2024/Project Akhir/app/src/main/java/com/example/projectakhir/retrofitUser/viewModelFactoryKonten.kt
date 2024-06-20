package com.example.projectakhir.retrofitUser

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.modulempat.utils.DepedencyInjection
import com.example.projectakhir.roomDatabasePariwisata.repositoryKonten

class viewModelFactoryKonten private constructor(private val repoSitory: Repository) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(viewModelKonten::class.java)){
            return viewModelKonten(repoSitory) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: viewModelFactoryKonten? = null
        fun getInstance(context: Context): viewModelFactoryKonten =
            instance ?: synchronized(this){
                instance ?: viewModelFactoryKonten(Repository())
            }.also { instance = it }
    }
}