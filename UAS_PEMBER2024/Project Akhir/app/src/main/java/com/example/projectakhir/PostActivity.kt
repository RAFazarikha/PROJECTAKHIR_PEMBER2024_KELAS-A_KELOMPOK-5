package com.example.projectakhir

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.example.modulempat.room.kontenViewModel
import com.example.modulempat.utils.reduceFileImage
import com.example.modulempat.utils.uriToFile
import com.example.projectakhir.login.Login
import com.example.projectakhir.roomDatabasePariwisata.kontenViewModelFactory
import com.example.projectakhir.roomDatabasePariwisata.tabelKonten
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class PostActivity : AppCompatActivity() {

    private var currentImageUri: Uri? = null

    private lateinit var kontenImage: ImageView

    private lateinit var appViewModel: kontenViewModel

    private lateinit var kontenDeskripsi: TextInputEditText

    private lateinit var kontenGambar: TextInputEditText

    private lateinit var dataUSer: Login


    private val imagePickerLauncher = registerImagePicker {
        val firstImage = it.firstOrNull() ?: return@registerImagePicker
        if (firstImage.uri.toString().isNotEmpty()) {
            // Menampilkan ImageView jika gambar berhasil dipilih
            kontenImage.visibility = View.VISIBLE
            // Menyimpan URI gambar yang dipilih
            currentImageUri = firstImage.uri
            // Menampilkan pesan bahwa gambar berhasil dimasukkan
            kontenGambar.setText("Change")

            // Menggunakan library Glide untuk menampilkan gambar yang dipilih
            Glide.with(kontenImage)
                .load(firstImage.uri)
                .into(kontenImage)
        } else {
            // Menyembunyikan ImageView jika tidak ada gambar yang dipilih
            View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        dataUSer = intent.getParcelableExtra<Login>("user")!!

        val factory = kontenViewModelFactory.getInstance(this)
        appViewModel = ViewModelProvider(this, factory)[kontenViewModel::class.java]

        kontenImage = findViewById(R.id.up_gambarBaru)
        kontenDeskripsi = findViewById(R.id.unggah_deskripsi)
        kontenGambar = findViewById(R.id.unggah_foto)

        onClick()
    }

    private fun onClick(){
        val openImagePicker = findViewById<TextInputEditText>(R.id.unggah_foto)
        openImagePicker.setOnClickListener {
            imagePickerLauncher.launch(
                ImagePickerConfig {
                    mode = ImagePickerMode.SINGLE
                    returnMode = ReturnMode.ALL
                    isFolderMode = true
                    folderTitle = "Galeri"
                    isShowCamera = false
                    imageTitle = "Tekan untuk memilih gambar"
                    doneButtonText = "Selesai"
                }
            )
        }
        val btnSavedPlayer = findViewById<MaterialButton>(R.id.tombol_update)
        btnSavedPlayer.setOnClickListener {
            // Memvalidasi input dan menyimpan data jika valid
            if (validateInput()) {
                savedData()
            }
        }

        val btnBack = findViewById<ImageButton>(R.id.back_button)
        btnBack.setOnClickListener{
            val intent = Intent(this, ForumActivity::class.java)
            startActivity(intent)
        }
    }
    private fun validateInput(): Boolean {
        var error = 0


        if (kontenDeskripsi.text.toString().isEmpty()) {
            error++
            kontenDeskripsi.error = "Nama pemain tidak boleh kosong"
        }



        if (kontenGambar.text.toString().isEmpty()) {
            error++
            kontenGambar.error = "Gambar tidak boleh kosong"
        }

        // Mengembalikan true jika tidak ada error, false jika ada error
        return error == 0

    }

    private fun savedData() {
        // Mengubah URI gambar menjadi file dan mengurangi ukuran file
        val imageFile = currentImageUri?.let { uriToFile(it, this).reduceFileImage() }

        val username: String = dataUSer.username.toString()
        val nama: String = dataUSer.nama.toString()


        val konten = imageFile?.let {
            tabelKonten(
                id = 0,
                username = username,
                nama = nama,
                deskrip = kontenDeskripsi.text.toString(),
                gambar_konten = imageFile
            )
        }


        if (konten != null) appViewModel.insertKonten(konten)

        // Menampilkan pesan bahwa data berhasil ditambahkan
        Toast.makeText(
            this@PostActivity,
            "Konten berhasil ditambahkan",
            Toast.LENGTH_SHORT
        ).show()

        // Mengakhiri activity
        finish()
    }
}