package com.example.projectakhir.roomDatabasePariwisata

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File


@Entity
data class tabelKonten(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "nama")
    val nama: String,

    @ColumnInfo(name = "deskrip")
    val deskrip: String,

    @ColumnInfo(name = "gambar_konten")
    val gambar_konten: File,

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        File(parcel.readString()!!),

    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(username)
        dest.writeString(nama)
        dest.writeString(deskrip)
        dest.writeString(gambar_konten.path)


    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<tabelKonten> {

        override fun createFromParcel(source: Parcel): tabelKonten {
            return tabelKonten(source)
        }


        override fun newArray(size: Int): Array<tabelKonten?> {
            return arrayOfNulls(size)
        }
    }
}
