package com.example.projectakhir.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectakhir.R
import com.example.projectakhir.retrofitUser.APIResponseUser


class adapterKonten(private val konten: List<APIResponseUser>) :
    RecyclerView.Adapter<adapterKonten.kontenViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: APIResponseUser)
    }

    class kontenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nama: TextView = itemView.findViewById(R.id.judul)
        val gambar: ImageView = itemView.findViewById(R.id.gambarkonten)
        val lokasi: TextView = itemView.findViewById(R.id.lokasi)
        val harga: TextView = itemView.findViewById(R.id.harga)
        val rate: TextView = itemView.findViewById(R.id.rate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): kontenViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.konten_wisata, parent, false)
        return kontenViewHolder(view)
    }

    override fun onBindViewHolder(holder: kontenViewHolder, position: Int) {
        val data = konten[position]

        holder.nama.text = data.nama
        holder.lokasi.text = data.lokasi
        holder.harga.text = data.tiket
        holder.rate.text = data.rating.toString()


        Glide.with(holder.gambar)
            .load(data.image)
            .into(holder.gambar)



        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(konten[holder.adapterPosition])}
    }

    override fun getItemCount(): Int = konten.size

}