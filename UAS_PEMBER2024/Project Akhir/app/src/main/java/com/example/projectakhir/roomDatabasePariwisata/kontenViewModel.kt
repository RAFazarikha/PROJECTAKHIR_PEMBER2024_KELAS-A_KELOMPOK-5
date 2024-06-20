package com.example.modulempat.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.projectakhir.roomDatabasePariwisata.repositoryKonten
import com.example.projectakhir.roomDatabasePariwisata.tabelKonten

class kontenViewModel(private val repoSitory: repositoryKonten) : ViewModel() {
    fun insertKonten(konten: tabelKonten){
        repoSitory.insertKonten(konten)
    }

    fun getAllKonten(): LiveData<List<tabelKonten>>{
        return repoSitory.getAllKonten()
    }

    fun deleteKonten(konten: tabelKonten) {
        repoSitory.deleteKonten(konten)
    }

    fun updateKonten(konten: tabelKonten){
        repoSitory.updateKonten(konten)
    }
}