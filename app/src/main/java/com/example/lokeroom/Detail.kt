package com.example.lokeroom

import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayInputStream

class Detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lowongandetail)

        val id_pekerjaan:String = intent.getStringExtra("id_pekerjaan").toString()


        val judul:TextView = findViewById(R.id.judul)
        val deskripsi:TextView = findViewById(R.id.deskripsi)

        val db:SQLiteDatabase = openOrCreateDatabase("loker", MODE_PRIVATE,null)
        val query = db.rawQuery("SELECT * FROM pekerjaan WHERE id_pekerjaan = '$id_pekerjaan'",null)
        query.moveToNext()

        val isi_judul = query.getColumnIndex("judul_pekerjaan")
        val isi_deskripsi = query.getColumnIndex("deskripsi_pekerjaan")
        val judul_fix = query.getString(isi_judul)
        val deskripsi_fix = query.getString(isi_deskripsi)

        judul.text = judul_fix
        deskripsi.text = deskripsi_fix
    }
}