package com.example.projectakhir.retrofitUser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    // Mendeklarasikan variabel _listKonten sebagai MutableLiveData yang berisi List dari ExampleAPIResponse
    private val _listKonten = MutableLiveData<List<APIResponseUser>>()
    // Mendeklarasikan variabel listKonten sebagai LiveData yang berisi List dari ExampleAPIResponse
    var listKonten: LiveData<List<APIResponseUser>> = _listKonten

    // Mendeklarasikan variabel _isLoading sebagai MutableLiveData yang berisi Boolean
    private var _isLoading = MutableLiveData<Boolean>()
    // Mendeklarasikan variabel isLoading sebagai LiveData yang berisi Boolean
    var isLoading: LiveData<Boolean> = _isLoading

    // Mendeklarasikan variabel _konten sebagai MutableLiveData yang berisi ExampleAPIResponse
    private val _konten = MutableLiveData<APIResponseUser>()
    // Mendeklarasikan variabel konten sebagai LiveData yang berisi ExampleAPIResponse
    var konten: LiveData<APIResponseUser> = _konten

    // Fungsi untuk mendapatkan semua pemain
    fun getAllKonten() {
        // Mengubah nilai _isLoading menjadi true
        _isLoading.value = true
        // Mendapatkan layanan API
        val service = APIConfigUser.getApiService().getAllTravel()
        // Mengirim request ke API
        service.enqueue(object : Callback<List<APIResponseUser>> {
            // Fungsi ini dipanggil ketika mendapatkan response dari API
            override fun onResponse(
                call: Call<List<APIResponseUser>>,
                response: Response<List<APIResponseUser>>
            ) {
                // Mengubah nilai _isLoading menjadi false
                _isLoading.value = false


                if (response.isSuccessful) {
                    _listKonten.value = response.body(); val responeBody = response.body()
                } else {
                    // Jika response gagal, log pesan error
                    Log.e("Error on Response", "onFailure: ${response.message()}")
                }
            }

            // Fungsi ini dipanggil ketika request ke API gagal
            override fun onFailure(call: Call<List<APIResponseUser>>, t: Throwable) {
                // Mengubah nilai _isLoading menjadi false
                _isLoading.value = false
                // Log pesan error
                Log.e("Error on Failure", "onFailure: ${t.message}")
            }

        })
    }
}