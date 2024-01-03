package com.example.lokeroom

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.cardview.widget.CardView
import org.w3c.dom.Text
import java.io.ByteArrayInputStream

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        val session:SharedPreferences = getSharedPreferences("pengguna", MODE_PRIVATE)
        val id_pelogin:String = session.getString("id_pengguna",null).toString()
        val db:SQLiteDatabase = openOrCreateDatabase("loker", MODE_PRIVATE,null)
        val query = db.rawQuery("SELECT * FROM pengguna WHERE id_pengguna = '$id_pelogin'",null)
        query.moveToNext()
        val foto:ByteArray? = query.getBlob(5)
        val nama_pelogin:String? = query.getString(1)
        val foto_profile:ImageView? = findViewById(R.id.profile_image)
        val nama:TextView = findViewById(R.id.pelogin)
        val card_akun:CardView = findViewById(R.id.card_akun);
        val card_perusahaan:CardView = findViewById(R.id.card_perusahaan);
        val card_lowongan:CardView = findViewById(R.id.card_lowongan);
        val card_logout:CardView = findViewById(R.id.card_logout);
        val btn_tambah:Button = findViewById(R.id.btn_tambah)

        try {
            val blobGambar = ByteArrayInputStream(foto)
            val gambarBitmap : Bitmap = BitmapFactory.decodeStream(blobGambar)
            foto_profile?.setImageBitmap(gambarBitmap)
        }catch (a:Exception){
            val gambarBitmap: Bitmap = BitmapFactory.decodeResource(this.resources,R.drawable.orang)
            foto_profile?.setImageBitmap(gambarBitmap)
        }

        nama?.text = nama_pelogin



        card_akun.setOnClickListener {
            val pindah:Intent = Intent(this, Akun::class.java);
            startActivity(pindah);
        }

        card_lowongan.setOnClickListener {
            val pindah:Intent = Intent(this, Lowongan::class.java);
            startActivity(pindah);
        }

        card_logout.setOnClickListener {
            val sesi = session.edit()
            sesi.clear()
            sesi.commit()
            val pindah:Intent = Intent(this,Login::class.java)
            startActivity(pindah)
            finish()
        }

        btn_tambah.setOnClickListener {
            val pindah:Intent = Intent(this,Tambah::class.java)
            startActivity(pindah)
        }

    }
}