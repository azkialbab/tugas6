package com.example.tugas6

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tugas6.databinding.ActivityResultBinding

class Result : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val nama = intent.getStringExtra("nama")
            val jam = intent.getStringExtra("jam")
            val tanggal = intent.getStringExtra("tanggal")
            val tujuan = intent.getStringExtra("tujuan")

            tnama.text      = "Nama          : $nama"
            tjam.text       = "Jam             : $jam"
            ttujuan.text    = "Tujuan         : $tujuan"
            ttanggal.text   = "Tanggal       : $tanggal"
        }
    }
}