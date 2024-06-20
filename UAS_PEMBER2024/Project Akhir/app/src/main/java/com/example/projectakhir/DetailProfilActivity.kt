package com.example.projectakhir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.projectakhir.login.Login
import com.example.projectakhir.roomDatabasePariwisata.tabelKonten

class DetailProfilActivity : AppCompatActivity() {

    private lateinit var dataUSer: Login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_profil)

        dataUSer = intent.getParcelableExtra<Login>("user")!!

        val user = findViewById<TextView>(R.id.username)
        val nama = findViewById<TextView>(R.id.nama)
        val prof = findViewById<TextView>(R.id.prof)
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val logout = findViewById<Button>(R.id.btnLogout)



        user.text = dataUSer.username
        nama.text = dataUSer.nama
        prof.text = dataUSer.profesi

        btnBack.setOnClickListener{
            val intent = Intent(this, BerandaActivity::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}