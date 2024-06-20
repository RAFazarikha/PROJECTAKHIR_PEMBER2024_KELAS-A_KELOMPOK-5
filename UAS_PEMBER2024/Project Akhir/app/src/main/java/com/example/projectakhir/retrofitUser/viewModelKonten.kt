package com.example.projectakhir.retrofitUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class viewModelKonten(private val repoSitory: Repository) : ViewModel() {
    val listKonten: LiveData<List<APIResponseUser>> = repoSitory.listKonten

    // Mendeklarasikan variabel isLoading yang berisi LiveData dari Boolean (status loading) dari repository
    val isLoading: LiveData<Boolean> = repoSitory.isLoading

    // Fungsi untuk mendapatkan semua pemain dari repository
    fun getAllKonten() {
        repoSitory.getAllKonten()
    }
}