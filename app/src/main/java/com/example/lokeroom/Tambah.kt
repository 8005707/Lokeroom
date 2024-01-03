package com.example.lokeroom

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class Tambah : AppCompatActivity() {
    var uriGambar: Uri? = null
    var bitmapGambar:Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tambah)

        val edt_judul:EditText = findViewById(R.id.edt_judul)
        val edt_deskripsi:EditText = findViewById(R.id.edt_Deskripsi)
        val btn_tambah:Button = findViewById(R.id.btn_tambah)
        val db:SQLiteDatabase = openOrCreateDatabase("loker", MODE_PRIVATE,null)
        val session:SharedPreferences = getSharedPreferences("pengguna", MODE_PRIVATE)
        val id_pelogin = session.getString("id_pengguna",null).toString()


        btn_tambah.setOnClickListener {
            val isi_judul: String = edt_judul.text.toString()
            val isi_deskripsi: String = edt_deskripsi.text.toString()
            val tambah =
                "INSERT INTO pekerjaan (judul_pekerjaan,deskripsi_pekerjaan,id_pengguna) VALUES (?,?,?)"
            val statement = db.compileStatement(tambah)
            statement.clearBindings()
            statement.bindString(1, isi_judul)
            statement.bindString(2, isi_deskripsi)
            statement.bindString(3, id_pelogin)
            statement.executeInsert()

            val pindah:Intent = Intent(this,Dashboard::class.java)
            startActivity(pindah)
        }
    }
}