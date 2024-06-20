package com.example.projectakhir.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectakhir.R
import com.example.projectakhir.roomDatabasePariwisata.tabelKonten
import com.google.android.material.imageview.ShapeableImageView

class adapterForum(private val kontenList: List<tabelKonten>) : RecyclerView.Adapter<adapterForum.kontenViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    private var stateFav = false

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onMoreClicked(data: tabelKonten, position: Int)
    }

    class kontenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val user: TextView = itemView.findViewById(R.id.usernm)
        val nama: TextView = itemView.findViewById(R.id.namafor)
        var deskrip: TextView = itemView.findViewById(R.id.kontendes)
        val gambar: ImageView = itemView.findViewById(R.id.gambar)
        val btnMore: ImageView = itemView.findViewById(R.id.more)
        val btnLike: ImageView = itemView.findViewById(R.id.love)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): kontenViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.konten_forum, parent, false)
        return kontenViewHolder(view)
    }

    override fun onBindViewHolder(holder: kontenViewHolder, position: Int) {
        val data = kontenList[position]

        holder.user.text = data.username
        holder.nama.text = data.nama
        holder.deskrip.text = data.deskrip


        val uri = Uri.fromFile(data.gambar_konten)
        holder.gambar.setImageURI(uri)

        holder.btnMore.setOnClickListener {
            onItemClickCallback.onMoreClicked(kontenList[holder.adapterPosition], holder.adapterPosition) }

        holder.btnLike.setOnClickListener {
            stateFav = !stateFav
            holder.btnLike.setImageResource(if (stateFav) R.drawable.love2 else R.drawable.love1)
        }

//
//        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(kontenList[holder.ab])}
    }

    override fun getItemCount(): Int = kontenList.size
}