package com.example.lokeroom

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.io.ByteArrayOutputStream

class Daftar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daftar)

        val edt_email: EditText = findViewById(R.id.edt_email)
        val edt_password: EditText = findViewById(R.id.edt_password)
        val edt_nama: EditText = findViewById(R.id.edt_nama)
        val edt_nomor: EditText = findViewById(R.id.edt_nomor)
        val btn_daftar: Button = findViewById(R.id.btn_daftar)

        val db:SQLiteDatabase = openOrCreateDatabase("loker", MODE_PRIVATE,null)

        btn_daftar.setOnClickListener {
            val isi_email: String = edt_email.text.toString()
            val isi_password: String = edt_password.text.toString()
            val isi_nama:String = edt_nama.text.toString()
            val isi_nomor:String =edt_nomor.text.toString()

            val tambah =
                "INSERT INTO pengguna (nama_pengguna,email_pengguna,pass_pengguna,nomor_pengguna) VALUES (?,?,?,?)"
            val statement = db.compileStatement(tambah)
            statement.clearBindings()
            statement.bindString(1, isi_nama)
            statement.bindString(2, isi_email)
            statement.bindString(3, isi_password)
            statement.bindString(4,isi_nomor)
            statement.executeInsert()

            val pindah: Intent = Intent(this,Login::class.java)
            startActivity(pindah)

        }

    }
}