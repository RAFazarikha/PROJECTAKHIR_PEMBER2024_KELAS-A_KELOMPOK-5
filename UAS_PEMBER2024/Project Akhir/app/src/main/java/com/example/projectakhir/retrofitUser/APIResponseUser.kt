package com.example.projectakhir.retrofitUser

import android.os.Parcel
import android.os.Parcelable
import com.example.projectakhir.roomDatabasePariwisata.tabelKonten
import com.google.gson.annotations.SerializedName


data class APIResponseUser(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("lokasi")
    val lokasi: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("tiket")
    val tiket: String,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("jam")
    val jam: String

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(nama)
        dest.writeString(lokasi)
        dest.writeString(deskripsi)
        dest.writeString(image)
        dest.writeString(tiket)
        dest.writeDouble(rating)
        dest.writeString(jam)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<APIResponseUser> {

        override fun createFromParcel(source: Parcel): APIResponseUser {
            return APIResponseUser(source)
        }


        override fun newArray(size: Int): Array<APIResponseUser?> {
            return arrayOfNulls(size)
        }
    }
}


