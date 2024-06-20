package com.example.projectakhir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.modulempat.PopUpActivity
import com.example.modulempat.room.kontenViewModel
import com.example.projectakhir.adapter.adapterForum
import com.example.projectakhir.login.Login
import com.example.projectakhir.roomDatabasePariwisata.kontenViewModelFactory
import com.example.projectakhir.roomDatabasePariwisata.tabelKonten
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ForumActivity : AppCompatActivity() {

    private lateinit var appViewModel: kontenViewModel

    private lateinit var kontenAdapter: adapterForum

    private lateinit var recyclerView: RecyclerView

    private lateinit var dataUSer: Login


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)
        val factory = kontenViewModelFactory.getInstance(this)
        appViewModel = ViewModelProvider(this, factory)[kontenViewModel::class.java]

        val dataUSer = intent.getParcelableExtra<Login>("user")!!

        recyclerView = findViewById(R.id.forum)


        appViewModel.getAllKonten().observe(this) {tabelkonten ->
            if (tabelkonten != null){
                kontenAdapter = adapterForum(tabelkonten)
                recyclerView.adapter = kontenAdapter


                kontenAdapter.setOnItemClickCallback(object :
                    adapterForum.OnItemClickCallback{
                    override fun onMoreClicked(data: tabelKonten, position: Int) {
                        PopUpActivity(data, position).show(supportFragmentManager, PopUpActivity.TAG)
                    }
                }
                )

            }
        }

        val btnAdd = findViewById<FloatingActionButton>(R.id.addButton)
        btnAdd.setOnClickListener{
            val intent = Intent(this, PostActivity::class.java)
            intent.putExtra("user", dataUSer)
            startActivity(intent)
        }


        val btnSpot = findViewById<ImageView>(R.id.spot)
        btnSpot.setOnClickListener{
            val intent = Intent(this, BerandaActivity::class.java)
            intent.putExtra("user", dataUSer)
            startActivity(intent)
        }
    }

//    private fun showSelectedPlayer(data: kontenDatabase) {
//        // Membuat intent untuk berpindah ke DetailPlayerActivity
//        val navigateToDetail = Intent(this, DetailPlayerActivity::class.java)
//
//        // Menambahkan dan membawa data pemain ke intent dengan tujuan ke DetailPlayerActivity
//        navigateToDetail.putExtra("player", data)
//
//        // Memulai activity baru
//        startActivity(navigateToDetail)
//    }




    override fun onRestart() {
        super.onRestart()

        // Memperbarui daftar pemain
        appViewModel.getAllKonten()
    }
}