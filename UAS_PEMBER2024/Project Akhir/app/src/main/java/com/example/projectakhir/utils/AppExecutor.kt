package com.example.modulempat.utils

import android.os.Handler
import android.os.Looper
import androidx.annotation.VisibleForTesting
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutor @VisibleForTesting constructor(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor
) {

    companion object {
        private const val THREAD_COUNT = 3
    }

    // Konstruktor default yang membuat Executor untuk diskIO, networkIO, dan mainThread
    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(THREAD_COUNT),
        MainThreadExecutor()
    )

    // Mendapatkan Executor untuk operasi I/O disk
    fun diskIO(): Executor = diskIO

    // Mendapatkan Executor untuk operasi I/O jaringan
    fun networkIO(): Executor = networkIO

    // Mendapatkan Executor untuk operasi yang berjalan di thread utama
    fun mainThread(): Executor = mainThread

    // Kelas internal yang mengimplementasikan Executor dan menjalankan command di thread utama
    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}