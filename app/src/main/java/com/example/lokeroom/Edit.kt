package com.example.lokeroom

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Edit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit)


        val id_pekerjaan:String = intent.getStringExtra("id_pekerjaan").toString()
        val db: SQLiteDatabase = openOrCreateDatabase("loker", MODE_PRIVATE,null)
        val query = db.rawQuery("SELECT * FROM pekerjaan WHERE id_pekerjaan = '$id_pekerjaan'",null)
        query.moveToNext()

        val edt_judul: EditText = findViewById(R.id.edt_judul)
        val edt_deskripsi: EditText = findViewById(R.id.edt_Deskripsi)
        val btn_ubah:Button = findViewById(R.id.btn_ubah)

        val judul:String = query.getString(1)
        val deskripsi:String = query.getString(2)

        edt_judul.setText(judul)
        edt_deskripsi.setText(deskripsi)

        btn_ubah.setOnClickListener {
            val isi_judul:String = edt_judul.text.toString()
            val isi_deskripsi:String = edt_deskripsi.text.toString()

            val edit =
                "UPDATE pekerjaan SET judul_pekerjaan = ?,deskripsi_pekerjaan = ? WHERE id_pekerjaan = '$id_pekerjaan'"
            val statement = db.compileStatement(edit)
            statement.clearBindings()
            statement.bindString(1, isi_judul)
            statement.bindString(2, isi_deskripsi)

            statement.executeUpdateDelete()

            val pindah: Intent = Intent(this,Lowongan::class.java)
            startActivity(pindah)
        }


    }
}