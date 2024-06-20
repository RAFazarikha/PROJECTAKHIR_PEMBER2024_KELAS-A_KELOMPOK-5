package com.example.projectakhir

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Durasi splash screen dalam milidetik (misalnya 3 detik)
        val splashScreenDuration = 2000L

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            // Setelah durasi selesai, pindah
            val intent = Intent(this, RegisActivity::class.java)
            startActivity(intent)
            finish()
        }, splashScreenDuration)
    }
}