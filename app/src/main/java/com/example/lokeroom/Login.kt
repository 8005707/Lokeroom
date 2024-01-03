package com.example.lokeroom

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val btn_login:Button = findViewById(R.id.btn_login)
        val edt_email:EditText = findViewById(R.id.edt_email)
        val edt_password:EditText = findViewById(R.id.edt_password)
        val txt_daftar:TextView = findViewById(R.id.daftar)

        txt_daftar.setOnClickListener {
            val pindah:Intent = Intent(this,Daftar::class.java)
            startActivity(pindah)
        }

        btn_login.setOnClickListener {

            val email:String = edt_email.text.toString()
            val password:String = edt_password.text.toString()

            val db:SQLiteDatabase = openOrCreateDatabase("loker", MODE_PRIVATE,null)
            val query = db.rawQuery("SELECT * FROM pengguna WHERE email_pengguna = '$email' AND pass_pengguna = '$password'",null)
            val test = query.moveToNext()

            if (test){
                val id_pengguna = query.getString(0)
                val session:SharedPreferences = getSharedPreferences("pengguna", MODE_PRIVATE)
                val sesi = session.edit()
                sesi.putString("id_pengguna",id_pengguna)
                sesi.commit()
                val pindah:Intent = Intent(this, Dashboard::class.java);
                startActivity(pindah);
            }else{
                Toast.makeText(this,"Email atau Password anda salah",Toast.LENGTH_LONG).show()
            }

        }

        }

}