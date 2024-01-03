package com.example.lokeroom

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class Hapus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hapus)

        val id_pekerjaan = intent.getStringExtra("id_pekerjaan").toString()
        val db: SQLiteDatabase = openOrCreateDatabase("loker", MODE_PRIVATE,null)
        val cek = db.rawQuery("DELETE FROM pekerjaan WHERE id_pekerjaan = '$id_pekerjaan'",null)

        if (cek.moveToNext()){
            Toast.makeText(this, "Data gagal dihapus ", Toast.LENGTH_LONG).show()
            val pindah: Intent = Intent(this,Lowongan::class.java)
            startActivity(pindah)
        } else{
            Toast.makeText(this, "Data Telah Dihapus ", Toast.LENGTH_LONG).show()
            val pindah: Intent = Intent(this,Lowongan::class.java)
            startActivity(pindah)
        }
    }
}