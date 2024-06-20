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
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.example.modulempat.room.kontenViewModel
import com.example.modulempat.utils.reduceFileImage
import com.example.modulempat.utils.uriToFile
import com.example.projectakhir.roomDatabasePariwisata.kontenViewModelFactory
import com.example.projectakhir.roomDatabasePariwisata.tabelKonten
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.io.File

class UpdateActivity : AppCompatActivity() {

    private var currentImageUri: Uri? = null
    private var oldImageUri: File? = null

    private lateinit var kontenImage: ImageView

    private lateinit var appViewModel: kontenViewModel

    private lateinit var kontenDeskripsi: TextInputEditText

    private lateinit var kontenGambar: TextInputEditText

    private lateinit var getData: tabelKonten

    private val imagePickerLauncher = registerImagePicker {
        val firstImage = it.firstOrNull() ?: return@registerImagePicker
        if (firstImage.uri.toString().isNotEmpty()) {
            // Menampilkan ImageView jika gambar berhasil dipilih
            kontenImage.visibility = View.VISIBLE
            // Menyimpan URI gambar yang dipilih
            currentImageUri = firstImage.uri
            // Menampilkan pesan bahwa gambar berhasil dimasukkan
            kontenGambar.setText("Change")
            kontenImage.setImageURI(currentImageUri)

            // Menggunakan library Glide untuk menampilkan gambar yang dipilih
//            Glide.with(kontenImage)
//                .load(firstImage.uri)
//                .into(kontenImage)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        getData = intent.getParcelableExtra("id")!!

        val factory = kontenViewModelFactory.getInstance(this)
        appViewModel = ViewModelProvider(this, factory)[kontenViewModel::class.java]

        kontenImage = findViewById(R.id.up_gambarBaru)
        kontenDeskripsi = findViewById(R.id.unggah_deskripsi_up)
        kontenGambar = findViewById(R.id.unggah_foto)

        kontenDeskripsi.setText(getData.deskrip)
        kontenGambar.setText("Change")

        oldImageUri = getData.gambar_konten
        val uri = Uri.fromFile(oldImageUri)
        kontenImage.setImageURI(uri)
        kontenImage.visibility = View.VISIBLE


        onClick()
    }

    private fun onClick(){
        val btnSave = findViewById<MaterialButton>(R.id.tombol_update)
        btnSave.setOnClickListener {
            if(validateInput()){
                savedData()
            }
        }

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


        val btnBack = findViewById<ImageButton>(R.id.back_button)
        btnBack.setOnClickListener{
            val intent = Intent(this, ForumActivity::class.java)
            startActivity(intent)
        }
    }
    private fun validateInput(): Boolean {
        var error = 0

        // Memeriksa apakah nama pemain kosong
        if (kontenDeskripsi.text.toString().isEmpty()) {
            error++
            kontenDeskripsi.error = "Deskripsi Konten Tidak Boleh Kosong"
        }


        // Memeriksa apakah gambar pemain kosong
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

        // Membuat objek pemain dengan data yang diinputkan
        val konten = (if (currentImageUri != null) imageFile else oldImageUri)?.let {
            tabelKonten(
                id = getData.id,
                username = getData.username,
                nama = getData.nama,
                deskrip = kontenDeskripsi.text.toString(),
                gambar_konten = it
            )
        }

        // Menyimpan data pemain ke database
        if (konten != null) appViewModel.updateKonten(konten)

        // Menampilkan pesan bahwa data pemain berhasil ditambahkan
        Toast.makeText(
            this@UpdateActivity,
            "Konten berhasil diubah",
            Toast.LENGTH_SHORT
        ).show()

        // Mengakhiri activity
        finish()
    }
}