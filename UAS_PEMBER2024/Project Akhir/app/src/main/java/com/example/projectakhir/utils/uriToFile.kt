package com.example.modulempat.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Ukuran maksimal file gambar yang diizinkan adalah 1 MB
private const val MAXIMAL_SIZE = 1000000 //1 MB

// Format nama file gambar yang akan dibuat
private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"

// Membuat timestamp dengan format yang telah ditentukan
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

/**
 * Fungsi ini digunakan untuk mendapatkan Uri gambar.
 * Jika versi Android adalah Q atau lebih tinggi, maka akan membuat ContentValues dan memasukkan ke content resolver.
 * Jika tidak, maka akan memanggil fungsi getImageUriForPreQ.
 */
fun getImageUri(context: Context): Uri {
    var uri: Uri? = null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/MyCamera/")
        }
        uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }
    return uri ?: getImageUriForPreQ(context)
}

/**
 * Fungsi ini digunakan untuk mendapatkan Uri gambar untuk versi Android sebelum Q.
 * Fungsi ini akan membuat file gambar baru dan mengembalikan Uri dari file tersebut.
 */
private fun getImageUriForPreQ(context: Context): Uri {
    val filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageFile = File(filesDir, "/MyCamera/$timeStamp.jpg")
    if (imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdir()
    return FileProvider.getUriForFile(
        context,
        "com.vinz.playerpedia.fileprovider",
        imageFile
    )
}

/**
 * Fungsi ini digunakan untuk membuat file sementara.
 * Fungsi ini akan membuat file baru di direktori cache dengan nama file berdasarkan timestamp.
 */
fun createCustomTempFile(context: Context): File {
    val filesDir = context.externalCacheDir
    return File.createTempFile(timeStamp, ".jpg", filesDir)
}

/**
 * Fungsi ini digunakan untuk mengubah Uri gambar menjadi File.
 * Fungsi ini akan membaca data dari Uri dan menulisnya ke file sementara.
 */
fun uriToFile(imageUri: Uri, context: Context): File {
    val myFile = createCustomTempFile(context)
    val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
    val outputStream = FileOutputStream(myFile)
    val buffer = ByteArray(1024)
    var length: Int
    while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
    outputStream.close()
    inputStream.close()
    return myFile
}

/**
 * Fungsi ini digunakan untuk mengubah File menjadi Uri.
 * Fungsi ini akan mengembalikan Uri dari file yang diberikan.
 */
fun fileToUri(context: Context, file: File): Uri {
    return FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", file)
}

/**
 * Fungsi ini digunakan untuk mengurangi ukuran file gambar.
 * Fungsi ini akan mengurangi kualitas gambar hingga ukuran file kurang dari 1 MB.
 */
fun File.reduceFileImage(): File {
    val file = this
    val bitmap = BitmapFactory.decodeFile(file.path).getRotatedBitmap(file)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAXIMAL_SIZE)
    bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

/**
 * Fungsi ini digunakan untuk mendapatkan Bitmap yang telah diputar berdasarkan orientasi gambar.
 * Fungsi ini akan membaca orientasi gambar dari Exif data dan memutar Bitmap sesuai dengan orientasi tersebut.
 */
fun Bitmap.getRotatedBitmap(file: File): Bitmap? {
    val orientation = ExifInterface(file).getAttributeInt(
        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED
    )
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(this, 90F)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(this, 180F)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(this, 270F)
        ExifInterface.ORIENTATION_NORMAL -> this
        else -> this
    }
}

/**
 * Fungsi ini digunakan untuk memutar Bitmap.
 * Fungsi ini akan membuat Bitmap baru yang telah diputar berdasarkan sudut yang diberikan.
 */
fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(
        source, 0, 0, source.width, source.height, matrix, true
    )
}