package com.example.modulempat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.modulempat.room.kontenViewModel
import com.example.projectakhir.R
import com.example.projectakhir.UpdateActivity
import com.example.projectakhir.roomDatabasePariwisata.kontenViewModelFactory
import com.example.projectakhir.roomDatabasePariwisata.tabelKonten
import com.google.android.material.button.MaterialButton

class PopUpActivity(private val kntnDatabase: tabelKonten, position: Int) : DialogFragment() {
    // Mendeklarasikan ViewModel untuk interaksi dengan database
    private lateinit var appViewModel: kontenViewModel

    // Fungsi getTheme digunakan untuk mendapatkan tema dialog.
    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    // Fungsi onStart dipanggil ketika dialog dimulai.
    // Fungsi ini digunakan untuk mengatur layout dialog.
    override fun onStart() {
        super.onStart()
        requireDialog().window?.apply {
            setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        view?.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(16, 16, 16, 16)
        }
    }

    // Fungsi onCreateView digunakan untuk membuat tampilan dialog.
    // Fungsi ini mengembalikan tampilan yang dibuat dari layout fragment_pop_up.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_pop_up, container, false)
    }

    // Fungsi onViewCreated dipanggil setelah tampilan dialog dibuat.
    // Fungsi ini digunakan untuk menginisialisasi ViewModel dan menangani aksi klik pada tombol ubah dan hapus.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mendapatkan instance ViewModel.
        val factory = kontenViewModelFactory.getInstance(requireContext())
        appViewModel = ViewModelProvider(this, factory)[kontenViewModel::class.java]

        // Mendapatkan referensi ke tombol ubah dan hapus.
        val btnUbah: MaterialButton = view.findViewById(R.id.btnUbah)
        val btnHapus: MaterialButton = view.findViewById(R.id.btnHapus)

        // Menangani aksi klik pada tombol ubah.
        // Ketika tombol ubah diklik, intent baru dibuat untuk memulai UpdatePlayerRoomActivity dan pemain yang dipilih dikirim sebagai extra.
        btnUbah.setOnClickListener {
            val intent = Intent(requireContext(), UpdateActivity::class.java)
            intent.putExtra("id", kntnDatabase)
            startActivity(intent)
            dismiss()
        }

        // Menangani aksi klik pada tombol hapus.
        // Ketika tombol hapus diklik, pemain yang dipilih dihapus dari database dan dialog ditutup.
        btnHapus.setOnClickListener {
            appViewModel.deleteKonten(kntnDatabase)
            dismiss()
        }
    }

    // Objek companion yang berisi tag untuk dialog.
    companion object {
        const val TAG = "PopUpFragment"
    }
}