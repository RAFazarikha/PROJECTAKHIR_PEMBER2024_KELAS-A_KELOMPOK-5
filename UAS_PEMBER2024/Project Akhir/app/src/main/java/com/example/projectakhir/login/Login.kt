package com.example.projectakhir.login

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Login(

    @field:SerializedName("nama")
    val nama: String?,

    @field:SerializedName("username")
    val username: String?,

    @field:SerializedName("profesi")
    val profesi: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(nama)
        dest.writeString(username)
        dest.writeString(profesi)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Login> {
        // Fungsi ini digunakan untuk membuat objek Food dari Parcel
        override fun createFromParcel(source: Parcel): Login {
            return Login(source)
        }

        // Fungsi ini digunakan untuk membuat array dari Food
        override fun newArray(size: Int): Array<Login?> {
            return arrayOfNulls(size)
        }
    }
}
