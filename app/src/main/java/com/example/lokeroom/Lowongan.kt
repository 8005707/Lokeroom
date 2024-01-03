package com.example.lokeroom

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayInputStream

class Lowongan : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lowongan)

        val rv_pekerjaan:RecyclerView = findViewById(R.id.rv_pekerjaan)
        val judul = mutableListOf<String>()
        val profile = mutableListOf<Bitmap>()
        val nomor = mutableListOf<String>()
        val id_lowongan = mutableListOf<String>()
        val id = mutableListOf<String>()
        val nama = mutableListOf<String>()

        val db:SQLiteDatabase = openOrCreateDatabase("loker", MODE_PRIVATE,null)
        val data = db.rawQuery("SELECT * FROM pekerjaan JOIN pengguna ON pekerjaan.id_pengguna = pengguna.id_pengguna",null)

        val nomor_lowongan = data.getColumnIndex("nomor_pengguna")
        val judul_lowongan = data.getColumnIndex("judul_pekerjaan")
        val foto = data.getColumnIndex("foto_pengguna")
        val lowongan = data.getColumnIndex("id_pekerjaan")
        val nama_lowongan = data.getColumnIndex("nama_pengguna")
        val id_postingan = data.getColumnIndex("id_pengguna")
        while (data.moveToNext()){
            try {
                val blobGambar = ByteArrayInputStream(data.getBlob(foto))
                val gambarBitmap : Bitmap = BitmapFactory.decodeStream(blobGambar)
                profile.add(gambarBitmap)
            }catch (a:Exception){
                val gambarBitmap: Bitmap = BitmapFactory.decodeResource(this.resources,R.drawable.orang)
                profile.add(gambarBitmap)
            }
            judul.add(data.getString(judul_lowongan))
            nomor.add(data.getString(nomor_lowongan))
            id_lowongan.add(data.getString(lowongan))
            nama.add(data.getString(nama_lowongan))
            id.add(data.getString(id_postingan))
        }
        val rv = Template(this,id_lowongan,judul,nomor,profile,id)
        rv_pekerjaan.layoutManager = LinearLayoutManager(this)
        rv_pekerjaan.adapter = rv
    }
}

