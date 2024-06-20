package com.example.projectakhir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.projectakhir.adapter.adapterKonten
import com.example.projectakhir.login.Login
import com.example.projectakhir.retrofitUser.APIResponseUser
import com.example.projectakhir.retrofitUser.viewModelFactoryKonten
import com.example.projectakhir.retrofitUser.viewModelKonten
import com.google.android.material.imageview.ShapeableImageView

class BerandaActivity : AppCompatActivity() {
    private lateinit var adapter: adapterKonten
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: viewModelKonten

    private lateinit var dataUSer: Login


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beranda)

        dataUSer = intent.getParcelableExtra<Login>("user")!!

        recyclerView = findViewById(R.id.recyclerberanda)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val factory = viewModelFactoryKonten.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[viewModelKonten::class.java]


        val userName = findViewById<TextView>(R.id.welcom)

        userName.text = "Welcome, " + dataUSer.username

        viewModel.getAllKonten()
        viewModel.listKonten.observe(this) { listkonten ->
            if (listkonten.isNotEmpty()) {
                adapter = adapterKonten(listkonten)
                recyclerView.adapter = adapter
                adapter.setOnItemClickCallback(object : adapterKonten.OnItemClickCallback {
                    override fun onItemClicked(data: APIResponseUser) {
                        startActivity(Intent(this@BerandaActivity, DetailWisataActivity::class.java).apply {
                            putExtra("data", data)
                        })
                    }

                })
            }
        }

        onClick()
    }

    private fun onClick(){
        val btnForum = findViewById<ImageView>(R.id.tombolforum)
        btnForum.setOnClickListener{
            val intent = Intent(this, ForumActivity::class.java)
            intent.putExtra("user", dataUSer)
            startActivity(intent)
        }

        val profil = findViewById<ShapeableImageView>(R.id.profil)
        profil.setOnClickListener{
            val intent = Intent(this, DetailProfilActivity::class.java)
            intent.putExtra("user", dataUSer)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllKonten()
    }
}