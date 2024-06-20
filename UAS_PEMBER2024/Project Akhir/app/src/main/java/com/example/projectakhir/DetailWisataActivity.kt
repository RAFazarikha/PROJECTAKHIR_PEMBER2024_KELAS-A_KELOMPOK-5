package com.example.projectakhir

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.projectakhir.retrofitUser.APIResponseUser
import com.example.projectakhir.roomDatabasePariwisata.tabelKonten
import java.io.File

class DetailWisataActivity : AppCompatActivity() {

    private lateinit var getData: APIResponseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_wisata)

        getData = intent.getParcelableExtra<APIResponseUser>("data")!!

        val gambar = findViewById<ImageView>(R.id.gambarDes)
        val judul = findViewById<TextView>(R.id.namades)
        val jam = findViewById<TextView>(R.id.jam)
        val harga = findViewById<TextView>(R.id.harga)
        val rate = findViewById<TextView>(R.id.rate)
        val deskripsi = findViewById<TextView>(R.id.des)
        val btnBack = findViewById<ImageView>(R.id.btnback)

        Glide.with(this)
            .load(getData.image)
            .into(gambar)

        judul.text = getData.nama
        jam.text = "Open " + getData.jam.toString()
        harga.text = "Ticket " + "\n" + getData.tiket.toString()
        rate.text = "Rating" + "\n" + getData.rating.toString()
        deskripsi.text = getData.deskripsi

        btnBack.setOnClickListener{
            val intent = Intent(this, BerandaActivity::class.java)
            startActivity(intent)
        }

        val btnShare: ImageView = findViewById<ImageButton>(R.id.btnShare)


        btnShare.setOnClickListener {

            // Membuat intent untuk berbagi teks
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${getData.nama}")
                type = "text/plain"
            }

            // Memeriksa apakah WhatsApp terinstal
            val whatsappInstalled = isPackageInstalled("com.whatsapp") || isPackageInstalled("com.whatsapp.w4b")
            if (whatsappInstalled) {

                // Jika WhatsApp terinstal, atur paket intent ke "com.whatsapp" dan mulai activity
                sendIntent.setPackage("com.whatsapp")
                startActivity(sendIntent)
            } else {

                // Jika WhatsApp tidak terinstal, tampilkan pesan toast
                Toast.makeText(this, "WhatsApp tidak terinstal.", Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun isPackageInstalled(packageName: String): Boolean {
        return try {
            // Mencoba mendapatkan informasi paket
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            // Jika paket tidak ditemukan, kembalikan false
            false
        }
    }

}